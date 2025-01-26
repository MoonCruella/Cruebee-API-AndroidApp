package androidapp.service.Impl;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
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
	

	@Override
	public String register(RegisterModel registerModel) {
		String otp = optUtil.generateOtp();
		try {
			emailUtil.sendOtpEmail(registerModel.getEmail(), otp);
		} catch (MessagingException e) {
			throw new RuntimeException("Unable to send otp please try again");
		}
		
		UserEntity user = new UserEntity();
		user.setUsername(registerModel.getUsername());
		user.setEmail(registerModel.getEmail());
		user.setPassword(registerModel.getPassword());
		user.setOtp(otp);
		user.setOptGeneratedTime(LocalDateTime.now());
		userRepository.save(user);
		return "User registration successful";
	}

	@Override
	public String verifyAccount(String email, String otp) {
		
		// Thiết lập thời gian để xác thực email là trong vòng 60s 
		UserEntity user = userRepository.findByEmail(email)
			.orElseThrow(()-> new RuntimeException("User not found with this email: " + email));
		if(user.getOtp().equals(otp) && Duration.between(user.getOptGeneratedTime(), LocalDateTime.now()).getSeconds() < (1 * 60)) {
			user.setActive(true);
			user.setRole("USER");
			userRepository.save(user);
			return "OTP verified. You can login";
		}
		return "Please regenerate otp and try again";
			
	}

	@Override
	public String regenerateOtp(String email) {
		
		UserEntity user = userRepository.findByEmail(email)
				.orElseThrow(()-> new RuntimeException("User not found with this email: " + email));
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
		UserEntity user = userRepository.findByEmail(loginModel.getEmail())
				.orElseThrow(() -> new RuntimeException("User not found with this email: " + loginModel.getEmail()));
		if(!loginModel.getPassword().equals(user.getPassword())) {
			return "Password is incorrect";
		}
		else if(!user.isActive()) {
			return "your account is not verified";
		}
		return "Login successful";
				
	}


	@Override
	public String verifyOtp(String email, String otp) {
		// Thiết lập thời gian để xác thực email là trong vòng 60s 
				UserEntity user = userRepository.findByEmail(email)
					.orElseThrow(()-> new RuntimeException("User not found with this email: " + email));
				if(user.getOtp().equals(otp) && Duration.between(user.getOptGeneratedTime(), LocalDateTime.now()).getSeconds() < (1 * 60)) {
					return "OTP Verified. You can set new password";
				}
				return "Please regenerate otp and try again";
	}

	@Override
	public String resetPassword(String email, String password, String repassword) {
		UserEntity user = userRepository.findByEmail(email)
				.orElseThrow(()-> new RuntimeException("User not found with this email: " + email));
		if(!password.equals(repassword)) {
			return "Password not match!";
		}
		user.setPassword(password);
		userRepository.save(user);
		return "Change password successful!";
	}


	
}
