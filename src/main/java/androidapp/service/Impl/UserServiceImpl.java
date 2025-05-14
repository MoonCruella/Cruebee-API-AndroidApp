package androidapp.service.Impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import androidapp.entity.TokenEntity;
import androidapp.model.AuthenticationResponse;
import androidapp.model.ChangePwRequest;
import androidapp.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import androidapp.entity.UserEntity;
import androidapp.model.LoginModel;
import androidapp.model.RegisterModel;
import androidapp.repository.UserRepository;
import androidapp.service.UserService;
import androidapp.util.EmailUtil;
import androidapp.util.OtpUtil;
import jakarta.mail.MessagingException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OtpUtil optUtil;
	
	@Autowired
	private EmailUtil emailUtil;

	@Autowired
	private JWTService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenRepository tokenRepository;

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	@Override
	public String register(RegisterModel registerModel) {

		// Kiểm tra nếu đã tồn tại user với email đó và đã active
		UserEntity userE = userRepository.findUsersByEmail(registerModel.getEmail());
		if(userE != null && userE.isActive()) {
			return "Email existed!!!";
		}
		String otp = optUtil.generateOtp();
		try {
			emailUtil.sendOtpEmail(registerModel.getEmail(), otp);
			UserEntity user = new UserEntity();
			user.setUsername(registerModel.getUsername());
			user.setEmail(registerModel.getEmail());
			user.setPassword(encoder.encode(registerModel.getPassword()));
			user.setOtp(otp);
			user.setOptGeneratedTime(LocalDateTime.now());
			user.setSdt(registerModel.getSdt());
			user.setGender(registerModel.getGender());

			// Kiểm tra nếu user đã đăng ký nhưng chưa active thì chỉ update thông tin cho user đó
			if(userE != null && !userE.isActive()){
				updateUser(user);
			}
			else{
				userRepository.save(user);
			}
			return "User registration successful";
		} catch (MessagingException e) {
			throw new RuntimeException("Unable to send otp please try again");
		}
	}

	@Override
	public AuthenticationResponse verifyAccount(String email, String otp) {
		UserEntity user = userRepository.findUsersByEmail(email);

		if (user == null) {
			return new AuthenticationResponse("Email not found");
		}

		if (user.getOtp() == null || !user.getOtp().equals(otp)) {
			return new AuthenticationResponse("Invalid OTP. Please check and try again.");
		}

		if (user.getOptGeneratedTime() == null || Duration.between(user.getOptGeneratedTime(), LocalDateTime.now()).getSeconds() > 180) {
			return new AuthenticationResponse("OTP has expired. Please regenerate and try again.");
		}

		user.setActive(true);
		user.setRole("USER");
		userRepository.save(user);

		String accessToken = jwtService.generateAccessToken(user.getEmail());
		String refreshToken = jwtService.generateRefreshToken(user.getEmail());
		saveUserToken(accessToken, refreshToken, user);

		return new AuthenticationResponse(accessToken, refreshToken,"OTP verified. You can login.");
	}


	@Override
	public String regenerateOtp(String email) {

		UserEntity user = userRepository.findUsersByEmail(email);
		if(user == null) {
			return "Email not existed!!!";
		}

		String otp = optUtil.generateOtp();
		try {
			emailUtil.sendOtpEmail(email, otp);
		} catch (MessagingException e) {
			throw new RuntimeException("Unable to send otp please try again");
		}
		user.setOtp(otp);
		user.setOptGeneratedTime(LocalDateTime.now());
		userRepository.save(user);
		return "Email sent ... please verify account within 3 minute";
		
	}

	@Override
	public String login(LoginModel loginModel) {
		JSONObject outputJsonObj = new JSONObject();
		//JWT : Check user => tạo token cho user
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginModel.getEmail(), loginModel.getPassword()));
			if(authentication.isAuthenticated()) {
				UserEntity user = userRepository.findUsersByEmail(loginModel.getEmail());
				String accessToken = jwtService.generateAccessToken(user.getEmail());
				String refreshToken = jwtService.generateRefreshToken(user.getEmail());
				JSONObject userJson = new JSONObject();
				userJson.put("id", user.getId());
				userJson.put("email", user.getEmail());
				userJson.put("username", user.getUsername());
				userJson.put("sdt", user.getSdt());
				userJson.put("gender",user.getGender());
				userJson.put("password", user.getPassword());
				outputJsonObj.put("user", userJson);
				outputJsonObj.put("token", accessToken);
				outputJsonObj.put("refresh_token",refreshToken);
				revokeAllTokenByUser(user);
				saveUserToken(accessToken, refreshToken, user);
			}

		} catch (BadCredentialsException e) {
			outputJsonObj.put("message", "Sai email hoặc mật khẩu!");
		}

		return outputJsonObj.toString();
	}

	@Override
	public String verifyOtp(String email, String otp) {
		// Fetch user from repository based on email
		UserEntity user = userRepository.findUsersByEmail(email);
		if(user == null) {
			return "Email not existed!!!";
		}

		// Check if OTP matches and if the OTP is within the valid time window (60 seconds)
		long otpDurationInSeconds = Duration.between(user.getOptGeneratedTime(), LocalDateTime.now()).getSeconds();
		if (user.getOtp().equals(otp)) {
			if (otpDurationInSeconds < 180) {
				return "OTP Verified. You can set new password";
			} else {
				return "OTP has expired. Please regenerate and try again.";
			}
		} else {
			return "Invalid OTP. Please check and try again.";
		}
	}

	@Override
	public String resetPassword(String email, String password) {
		UserEntity user = userRepository.findUsersByEmail(email);
		if(user == null) {
			return "Email not existed!!!";
		}
		user.setPassword(encoder.encode(password));
		userRepository.save(user);
		return "Change password successful!";
	}

	public void updateUser(UserEntity user) {
		UserEntity existing = userRepository.findUsersByEmail(user.getEmail());
		existing.setUsername(user.getUsername());
		existing.setPassword(user.getPassword());
		existing.setOtp(user.getOtp());
		existing.setOptGeneratedTime(LocalDateTime.now());
		existing.setSdt(user.getSdt());
		existing.setGender(user.getGender());
		userRepository.save(existing);
	}

	public ResponseEntity<Map<String, String>> refreshToken(
			HttpServletRequest request,
			HttpServletResponse response) {
		// extract the token from authorization header
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Refresh token is missing"));
		}

		String token = authHeader.substring(7);

		// extract username from token
		String username = jwtService.extractUserName(token);

		// check if the user exist in database
		UserEntity user = userRepository.findByEmail(username)
				.orElseThrow(()->new RuntimeException("No user found"));

		// check if the token is valid
		if(jwtService.isValidRefreshToken(token, user)) {
			// generate access token
			String accessToken = jwtService.generateAccessToken(user.getEmail());
			String refreshToken = jwtService.generateRefreshToken(user.getEmail());

			Map<String, String> tokens = new HashMap<>();
			tokens.put("accessToken", accessToken);
			tokens.put("refreshToken", refreshToken);

			revokeAllTokenByUser(user);
			saveUserToken(accessToken, refreshToken, user);

			return ResponseEntity.ok(tokens);
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Invalid refresh token"));
	}

	private void revokeAllTokenByUser(UserEntity user) {
		List<TokenEntity> validTokens = tokenRepository.findAllAccessTokensByUser(user.getId());
		if(validTokens.isEmpty()) {
			return;
		}

		validTokens.forEach(t-> {
			t.setLoggedOut(true);
		});

	}
	private void saveUserToken(String accessToken, String refreshToken, UserEntity user) {
		TokenEntity token = new TokenEntity();
		token.setAccessToken(accessToken);
		token.setRefreshToken(refreshToken);
		token.setLoggedOut(false);
		token.setUser(user);
		tokenRepository.save(token);
	}

	@Override
	public void updateAccount(UserEntity user) {
		UserEntity existing = userRepository.findUsersById((user.getId()));
		existing.setUsername(user.getUsername());
		existing.setSdt(user.getSdt());
		existing.setGender(user.getGender());
		userRepository.save(existing);
	}

	@Override
	public String changePw(ChangePwRequest changePwRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(changePwRequest.getEmail(), changePwRequest.getPassword()));
			if (authentication.isAuthenticated()) {
				UserEntity user = userRepository.findUsersByEmail(changePwRequest.getEmail());
				user.setPassword(encoder.encode(changePwRequest.getNewPassword()));
				userRepository.save(user);
			}
		} catch (BadCredentialsException e) {
			return "Wrong password!";
		}
		return "Change password successful!";
	}

	@Override
	public String deleteAccount(LoginModel loginModel) {
		String mes = null;
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginModel.getEmail(), loginModel.getPassword()));
			if(authentication.isAuthenticated()) {
				UserEntity user = userRepository.findUsersByEmail(loginModel.getEmail());
				userRepository.delete(user);
				mes =  "Delete Success!";
			}

		} catch (BadCredentialsException e) {
			mes =  "Wrong password!";
		}
		return mes;
	}

}
