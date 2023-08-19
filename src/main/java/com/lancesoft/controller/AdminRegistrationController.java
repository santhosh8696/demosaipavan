package com.lancesoft.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.lancesoft.dto.AdminRegistrationDto;
import com.lancesoft.entity.AdminRegistrationEntity;
import com.lancesoft.service.AdminService;

public class AdminRegistrationController {
	
	@Autowired
	AdminService adminregservice;
	
	@PostMapping("/register")
	public ResponseEntity<String> addAccount(@RequestBody @Valid AdminRegistrationDto registrationdto) {

		AdminRegistrationEntity reg = adminregservice.addReg(registrationdto);

		return new ResponseEntity<String>("Hii user " +  " Saved Sucessfully", HttpStatus.OK);

	}
	
	
	
}
