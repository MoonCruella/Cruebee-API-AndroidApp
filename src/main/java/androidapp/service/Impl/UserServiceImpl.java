package androidapp.service.Impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidapp.entity.CartItemsEntity;
import androidapp.entity.ProductEntity;
import androidapp.repository.CartItemRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
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
		} catch (MessagingException e) {
			throw new RuntimeException("Unable to send otp please try again");
		}
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
	}

	@Override
	public String verifyAccount(String email, String otp) {

		UserEntity user = userRepository.findUsersByEmail(email);
		if(user == null) {
			return "Email not existed!!!";
		}

		// Thiết lập thời gian để xác thực email là trong vòng 180s
		if(user.getOtp().equals(otp) && Duration.between(user.getOptGeneratedTime(), LocalDateTime.now()).getSeconds() < (1 * 60)) {
			user.setActive(true);
			user.setRole("USER");
			userRepository.save(user);
			return "OTP verified. You can login";
		}
		else if(!user.getOtp().equals(otp))
		{
			return "Invalid OTP. Please check and try again.";
		}
		return "OTP has expired. Please regenerate and try again.";
			
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
		return "Email sent ... please verify account within 1 minute";
		
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
				String token = jwtService.generateToken(user.getEmail());

				outputJsonObj.put("token", token);
				outputJsonObj.put("username", user.getUsername());
				outputJsonObj.put("userId", user.getId());
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
			if (otpDurationInSeconds < 60) {
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


	
}
