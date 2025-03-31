package androidapp.service;

import androidapp.entity.ProductEntity;
import androidapp.entity.UserEntity;
import androidapp.model.LoginModel;
import androidapp.model.RegisterModel;

import java.util.Map;

public interface UserService {
	
	String register(RegisterModel registerModel);
	String verifyAccount(String email, String otp);
	String regenerateOtp(String email);
	String login(LoginModel loginModel);
	String verifyOtp(String email, String otp);
	String resetPassword(String email, String password);
}
