package com.lancesoft.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.lancesoft.entity.Authorities;
import com.lancesoft.entity.RegistrationEntity;

public class UserDetailImple implements UserDetails {

	RegistrationEntity entity;
	
	
	
	public UserDetailImple(RegistrationEntity entity) {
		super();
		this.entity = entity;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
List<Authorities> list=    entity.getAuthorities();

List<SimpleGrantedAuthority> simple= new ArrayList<SimpleGrantedAuthority>();

for(Authorities au:list)
{
	simple.add(new SimpleGrantedAuthority(au.getRole()));
}
return simple;

	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return entity.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return entity.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
