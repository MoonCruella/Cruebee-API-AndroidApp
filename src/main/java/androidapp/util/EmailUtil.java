package androidapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtil {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendOtpEmail(String email,String otp) throws MessagingException {
		
		
		 MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		 MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
		 mimeMessageHelper.setTo(email);
		 mimeMessageHelper.setSubject("Verify OTP");
		 String htmlContent = """
				    <div>
				        <p>Click the link below to verify your account:</p>
				        <a href="http://localhost:8888/verify-account?email=%s&otp=%s" target="_blank">Click here to verify</a>
				    </div>
				""".formatted(email, otp);

		 mimeMessageHelper.setText(htmlContent, true);
		 javaMailSender.send(mimeMessage);
		 System.out.println("Mail Success!");
	
	}
}
