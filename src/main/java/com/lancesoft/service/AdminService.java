package com.lancesoft.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lancesoft.customexception.RegistrationCustomException;
import com.lancesoft.dao.AdminRegistrationDao;
import com.lancesoft.dto.AdminRegistrationDto;
import com.lancesoft.entity.AdminRegistrationEntity;
import com.lancesoft.entity.Authorities;

@Service
public class AdminService {

	@Autowired
	AdminRegistrationDao registrationRepo;


	
	public AdminRegistrationEntity addReg(AdminRegistrationDto registrationdto) {

		ModelMapper mapper = new ModelMapper();
		AdminRegistrationEntity registrationEntity = new AdminRegistrationEntity();
		
		if (registrationdto == null) {
			throw new RuntimeException("null found in registration plss check");
		} else if (registrationRepo.existsByUserName(registrationdto.getUserName())) {
			throw new RegistrationCustomException("707", "Username Already Exists please enter unique");
		} else

		{
			
			mapper.map(registrationdto, registrationEntity);
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

			registrationEntity.setPassword(bCryptPasswordEncoder.encode(registrationEntity.getPassword()));
			Authorities authority = new Authorities();
			authority.setRole("USER");
			List<Authorities> authorities = new ArrayList<Authorities>();
			authorities.add(authority);
			registrationEntity.setAuthorities(authorities);
			registrationRepo.save(registrationEntity);
			return registrationEntity;
		}

	}


	
	
	
}
