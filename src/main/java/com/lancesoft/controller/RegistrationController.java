package com.lancesoft.controller;

import java.text.ParseException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.lancesoft.dto.JwtLoginDto;
import com.lancesoft.dto.RegistrationDto;
import com.lancesoft.dto.RegistrationVerfication;
import com.lancesoft.jwt.JwtTokenString;
import com.lancesoft.jwt.JwtUtil;
import com.lancesoft.payload.ForgotPasscode;
import com.lancesoft.payload.OtpValidationPayload;
import com.lancesoft.payload.TwilioPasscode;
import com.lancesoft.payload.TwilioPasscodeValidation;
import com.lancesoft.service.JavaMailService;
import com.lancesoft.service.OtpValidationService;
import com.lancesoft.service.RegistrationService;
import com.lancesoft.service.TwilioOtpService;

@CrossOrigin("*")

@RestController
@RequestMapping("/api")

public class RegistrationController {

	@Autowired
	RegistrationService registrationService;

	@Autowired
	JavaMailService mailService;

//	@Autowired
//	ForgotPassword forgotPassword;
	@Autowired
	OtpValidationService otpvalid;

	@Autowired
	TwilioOtpService twilioservice;

	@Autowired
	HttpSession session;

	@Autowired
	JwtUtil jwtUtil;


	@Autowired
	AuthenticationManager authenticationManager;

	@PostMapping("/user/sendOtp")
	public void checkEmail(@RequestBody RegistrationVerfication verification) throws ParseException {
		twilioservice.sendRegisterOtp(verification);
	}

	@PostMapping("/user/register")
	public ResponseEntity<String> addAccount(@RequestBody @Valid RegistrationDto registrationDto) {
		System.out.println("called");
		ResponseEntity reg = registrationService.addReg(registrationDto);
		return reg;

	}

//	@PutMapping("/forgot")
//	public void   changePassword(@RequestBody ForgotPasswordPayload payload) {
//			
//		
//		  forgotPassword.setPassword(payload);			
//			
//
//	}
//	
	@PostMapping("/email")
	public ResponseEntity checkEmail(@RequestBody ForgotPasscode forgotPasscode) {
		System.out.println("hii");
		ResponseEntity res = registrationService.CheckingEmail(forgotPasscode.getEmail());
		System.out.println(res);
		mailService.sendMail(forgotPasscode.getEmail());
		return res;

	}

	@PostMapping("/validate")
	public ResponseEntity checkEmail(@RequestBody OtpValidationPayload otpValidationPayload) {
		ResponseEntity res = otpvalid.reset(otpValidationPayload);
		System.out.println(res);
		return res;
	}

	@PostMapping("/verifyPhone")
	public ResponseEntity checkMobile(@RequestBody TwilioPasscode forgotPasscode) throws ParseException {
		ResponseEntity res = twilioservice.send(forgotPasscode);
		System.out.println(res);
		return res;
	}

	@PostMapping("/validatephone")
	public ResponseEntity checkMobile(@RequestBody TwilioPasscodeValidation forgotPasscodevalidation)
			throws ParseException {
		ResponseEntity res = twilioservice.validate(forgotPasscodevalidation);
		return new ResponseEntity("updated", HttpStatus.OK);
	}

	@PostMapping("/login")
	public JwtTokenString generateToken(@RequestBody JwtLoginDto authRequest) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
		} catch (Exception ex) {
			throw new Exception("invalid username/password");
		}
		JwtTokenString jwtTokenString = new JwtTokenString();
		jwtTokenString.setToken(jwtUtil.generateToken(authRequest.getUserName()));
		return jwtTokenString;
	}


}
