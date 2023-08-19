package com.lancesoft.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lancesoft.dao.RegistrationRepo;
import com.lancesoft.entity.Authorities;
import com.lancesoft.entity.RegistrationEntity;

@Service
public class MyUserDetails implements UserDetailsService {

	@Autowired
	RegistrationRepo registrationRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		RegistrationEntity reg=       registrationRepo.findByUserName(username);
		List<Authorities>authority=reg.getAuthorities();
		System.out.println(authority);
		if(reg==null)
		{
			throw new RuntimeException("exception raised in my userdetails");
		}
		
		return new UserDetailImple(reg);
		
		
	}

}
