package androidapp.service;

import androidapp.model.LoginModel;
import androidapp.model.RegisterModel;

public interface UserService {
	
	String register(RegisterModel registerModel);
	String verifyAccount(String email, String otp);
	String regenerateOtp(String email);
	String login(LoginModel loginModel);
	String verifyOtp(String email, String otp);
	String resetPassword(String email, String password, String repassword);
}
