package com.lancesoft.service;

import java.text.ParseException;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lancesoft.dao.RegistrationRepo;
import com.lancesoft.dto.RegistrationVerfication;
import com.lancesoft.entity.RegistrationEntity;
import com.lancesoft.payload.TwilioPasscode;
import com.lancesoft.payload.TwilioPasscodeValidation;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

@Service
public class TwilioOtpService {

	@Autowired
	HttpSession session;

	@Autowired
	RegistrationRepo repo;

	@Autowired
	BCryptPasswordEncoder encoder;

	int i;

	String phoneNumber;

	private final String ACCOUNT_SID = "AC4c8b81d6389deeba0d1db6dde9cf9849";
	private final String token = "1c428ac58ee45e5c994fec9cdf8b0345";
	private final String trialNumber = "+16622462920";

	public ResponseEntity send(TwilioPasscode passcode) throws ParseException {
		sendOtp(passcode.getPhoneNumber());
		return new ResponseEntity(HttpStatus.OK);
	}

	public ResponseEntity sendRegisterOtp(RegistrationVerfication verification) throws ParseException {

		sendOtp(verification.getPhoneNumber());
		return new ResponseEntity(HttpStatus.OK);
	}

	public void sendOtp(String phoneNumber) {

		this.phoneNumber = phoneNumber;

		if (phoneNumber != null)
			Twilio.init(ACCOUNT_SID, token);

		Random r = new Random();
		this.i = r.nextInt(9999);

		if (this.i < 1000) {
			this.i += 1000;
		}

		String message = "your otp is " + i;

		MessageCreator creator = Message.creator(new PhoneNumber(phoneNumber), new PhoneNumber(trialNumber), message);
		System.out.println(phoneNumber);
		// creator.create();
		System.out.println(i);
		session.setAttribute("phone", phoneNumber);
		session.setAttribute("otp", String.valueOf(i));
		session.setMaxInactiveInterval(5 * 60);

	}

	public ResponseEntity validate(TwilioPasscodeValidation passcode) throws ParseException {

		if (session.getAttribute("otp2").equals(passcode.getOtp())) {
			RegistrationEntity reg = repo.findByPhoneNumber(session.getAttribute("phone").toString());
			reg.setPassword(encoder.encode(passcode.getPassword().toString()));
			repo.save(reg);
			session.invalidate();
			return new ResponseEntity("password is changed", HttpStatus.OK);
		} else {
			return new ResponseEntity("wrong otp", HttpStatus.BAD_REQUEST);
		}
	}

	

}
