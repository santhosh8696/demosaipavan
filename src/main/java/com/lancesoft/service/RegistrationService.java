package com.lancesoft.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lancesoft.customexception.RegistrationCustomException;
import com.lancesoft.dao.AdminRegistrationDao;
import com.lancesoft.dao.RegistrationRepo;
import com.lancesoft.dto.RegistrationDto;
import com.lancesoft.entity.Authorities;
import com.lancesoft.entity.RegistrationEntity;

@Service
public class RegistrationService {

	@Autowired
	RegistrationRepo registrationRepo;

	@Autowired
	TwilioOtpService otpService;

	@Autowired
	HttpSession httpSession;
	
	@Autowired
	HttpServletResponse httpServletResponse;

	public RegistrationService(RegistrationRepo registrationRepo) {
		super();
		this.registrationRepo = registrationRepo;
	}

	public ResponseEntity addReg(RegistrationDto registrationdto) {
		
		int i=otpService.i;
		
		String phoneNumber=otpService.phoneNumber;
		
		httpSession.setAttribute("otp2", String.valueOf(i));
		httpSession.setAttribute("phone", phoneNumber);
		httpSession.setMaxInactiveInterval(5 * 60);
		
		ModelMapper mapper = new ModelMapper();
		RegistrationEntity registrationEntity = new RegistrationEntity();

		System.err.println(httpSession.getAttribute("otp2"));	
		 
		if (registrationdto == null) {
			throw new RuntimeException("null found in registration plss check");
		} else if (registrationRepo.existsByUserName(registrationdto.getUserName())
				|| registrationRepo.existsByPhoneNumber(registrationdto.getPhoneNumber())) {
			throw new RegistrationCustomException("707", "Username Already Exists please enter unique");
		} else

		{
			httpSession.setAttribute("otp2", String.valueOf(i));
			httpSession.setMaxInactiveInterval(5 * 60);
			
			System.err.println(httpSession.getAttribute("otp2"));
			
			mapper.map(registrationdto, registrationEntity);
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			System.err.println(httpSession.getAttribute("otp2").equals(
					registrationdto.getUserOtp()));
			if (httpSession.getAttribute("otp2").equals(
			registrationdto.getUserOtp()) && httpSession.getAttribute("phone").equals(registrationdto.getPhoneNumber())) {
				
				registrationEntity.setPassword(bCryptPasswordEncoder.encode(registrationEntity.getPassword()));
				Authorities authority = new Authorities();
				authority.setRole("USER");
				List<Authorities> authorities = new ArrayList<Authorities>();
				authorities.add(authority);
				registrationEntity.setAuthorities(authorities);
				System.out.println(registrationdto);
				registrationRepo.save(registrationEntity);
				httpSession.invalidate();
				return new ResponseEntity(HttpStatus.ACCEPTED);

			} else {

				return new ResponseEntity(HttpStatus.BAD_REQUEST);
			}

		}

	}

	public ResponseEntity CheckingEmail(String email) {

		if (registrationRepo.existsByEmail(email)) {
			System.out.println(email);

			return new ResponseEntity<>(HttpStatus.OK);
		}

		else {
			return new ResponseEntity<String>("email Not Found", HttpStatus.BAD_GATEWAY);
		}
	}

}
