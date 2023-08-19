package com.lancesoft.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lancesoft.dao.RegistrationRepo;
import com.lancesoft.entity.RegistrationEntity;
import com.lancesoft.payload.ForgotPasscode;
import com.lancesoft.payload.OtpValidationPayload;

@Service
public class OtpValidationService {
	@Autowired
	HttpSession session;

	@Autowired
	RegistrationRepo repo;

	@Autowired
	ForgotPasscode passcode;

	@Autowired
	BCryptPasswordEncoder encoder;

	public ResponseEntity reset(OtpValidationPayload payload) {
		try
		{if (session.getAttribute("otp").equals(payload.getOtp().toString())) {
			String reg = String.valueOf(session.getAttribute("to"));
			RegistrationEntity reg2 = repo.findByEmail(reg);
			reg2.setPassword(encoder.encode(payload.getPassword().toString()));
			RegistrationEntity reg3 = repo.save(reg2);
			return new ResponseEntity<String>("Reset", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Wrong Otp", HttpStatus.BAD_REQUEST);
		}
		
		}catch (Exception e) {
			return new ResponseEntity<String>("Error Occured", HttpStatus.BAD_REQUEST);
		}

	}
}
