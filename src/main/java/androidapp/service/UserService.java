package androidapp.service;

import androidapp.entity.UserEntity;
import androidapp.model.AuthenticationResponse;
import androidapp.model.ChangePwRequest;
import androidapp.model.LoginModel;
import androidapp.model.RegisterModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserService {
	
	String register(RegisterModel registerModel);
	AuthenticationResponse verifyAccount(String email, String otp);
	String regenerateOtp(String email);
	String login(LoginModel loginModel);
	String verifyOtp(String email, String otp);
	String resetPassword(String email, String password);
	ResponseEntity refreshToken(HttpServletRequest request, HttpServletResponse response);
	void updateAccount(UserEntity userEntity);
	String changePw(ChangePwRequest changePwRequest);
	String deleteAccount(LoginModel loginModel);
}
