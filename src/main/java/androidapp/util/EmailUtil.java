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
                    <p>Hello,</p>
                    <p>Your OTP to verify your account is: <strong>%s</strong></p>
                    <p>Thank you!</p>
                </div>
                """.formatted(otp);

		 mimeMessageHelper.setText(htmlContent, true);
		 javaMailSender.send(mimeMessage);
		 System.out.println("Mail Success!");
	
	}
}
