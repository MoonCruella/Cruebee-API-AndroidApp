package androidapp.util;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class OtpUtil {
	
	public String generateOtp() {
		
		// Tạo random 1 số từ 0 đến 999999
		Random random = new Random();
		int randomNumber = random.nextInt(999999); 
		String output = Integer.toString(randomNumber);
		
		// Xử lý nếu random số đó không đủ 6 chữ số thì thêm số 0 vào đầu 
		while(output.length() < 6) {
			output = "0" + output;
		}
		return output;
	}
}
