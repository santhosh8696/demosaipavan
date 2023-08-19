package com.lancesoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lancesoft.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;

	
	@GetMapping("/")
	public String gettd() {
		return "Hlo";
	}

	
	
}
