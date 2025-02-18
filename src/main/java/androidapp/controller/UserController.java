package androidapp.controller;
import androidapp.entity.CategoryEntity;
import androidapp.service.CategoryService;
import androidapp.service.Impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import androidapp.model.LoginModel;
import androidapp.model.RegisterModel;
import androidapp.service.UserService;

import java.util.List;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;


    @Operation(summary = "Test program",description = "Say Hello World",tags = "Get")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "See Hello World",content = {
                    @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not See",content = @Content)
    })
    @GetMapping("/hello")
    public String index() {
        return "Hello World";
    }

    @PostMapping("/loginn")
    public ResponseEntity<?> login(@RequestBody LoginModel loginModel) {
    	return new ResponseEntity<>(userService.login(loginModel),HttpStatus.OK);
    }
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterModel registerModel){
    	return new ResponseEntity<>(userService.register(registerModel),HttpStatus.OK);
    }
    
    // Sử dụng phương thức put: put để cập nhật 1 resource đã tồn tại trên server theo uri đã cung cấp và body kèm theo. 
    // Cập nhật thuộc tính active của User từ 0 thành 1 nếu active thành công
    @PutMapping("/verify-account")
    public ResponseEntity<String> verifyAccount(@RequestParam String email, @RequestParam String otp){
    	return new ResponseEntity<>(userService.verifyAccount(email,otp),HttpStatus.OK);
    }
    
    // Cập nhật thuộc tính otp của User khi tạo lại otp mới 
    @PutMapping("/regenerate-otp")
    public ResponseEntity<String> regenerateOtp(@RequestParam String email){
    	return new ResponseEntity<>(userService.regenerateOtp(email),HttpStatus.OK);
    }
    
  
    @PutMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestParam String email ){
    	return new ResponseEntity<>(userService.regenerateOtp(email),HttpStatus.OK);
    }
    
    @PutMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam("email") String email, @RequestParam("otp") String otp){
    	return new ResponseEntity<>(userService.verifyOtp(email,otp),HttpStatus.OK);
    }
    
    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email, @RequestParam String password){
    	return new ResponseEntity<>(userService.resetPassword(email,password),HttpStatus.OK);
    }

    
}


	

	