package com.lancesoft.service;

import java.io.File;
import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.lancesoft.payload.ForgotPasscode;

@Service
public class JavaMailService {
	int i;
	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	SimpleMailMessage mailMessage;

	@Autowired
	HttpSession session;

	public void sendMail(String to) {

		Random r = new Random();

		i = r.nextInt(9999);
		if (i < 1000) {
			i += 1000;
		}
		System.out.println(i);

		System.out.println(r);
		try {

			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");

			message.setFrom(new InternetAddress("onlinesupermarket37@gmail.com"));
			message.setTo(to);
			message.setSubject("Password Reset for OMG");
			message.setText(" <!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "  <head>\r\n"
					+ "    <meta charset=\"UTF-8\">\r\n"
					+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
					+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\r\n" + "   \r\n"
					+ "    </head>\r\n" + "  <body>\r\n"
					+ "      <html> <p> Password Reset of OMG Is here please refer </p>\r\n"
					+ "<center><img style=\"background-color:#2c5f2d; width:150px; height: 50px;\" src='cid:image' alt=\"test\"/></center>\r\n"
					+ "<h2><center style=\'font-weight: bold\'>Verification code</center> <font color=#ff4500><center>"
					+ i + "</center></font></h2><p><center>Here is your OTP verification code.</center></p></font>\r\n"
					+ "<footer>If you didn't try to sign in just now, please <nobr style=\"color: rgb(9, 132, 209); \"> change your password</nobr> to protect your account.</footer>\r\n"
					+ "<hr >\r\n" + "<p>Sent by <font style=\"color: blue\"> OMG</font> </p>\r\n"
					+ "<p><font style=\"color: blue\"> Support Center Privacy Policy</font> </p>\r\n" + "</html>\r\n"
					+ "      </body>\r\n" + "</html>", true);

			message.addInline("image", new ClassPathResource("omg.png"));

			// Send message
			javaMailSender.send(mimeMessage);
			System.out.println("message sent successfully....");

			String otp = String.valueOf(i);
			this.session.setAttribute("otp", otp);
			this.session.setAttribute("to", to);
			this.session.setMaxInactiveInterval(5*60);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
