package com.lancesoft.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lancesoft.entity.RegistrationEntity;

@Repository
public interface RegistrationRepo extends JpaRepository<RegistrationEntity, Integer> {

	public RegistrationEntity findByUserName(String userName);

	public Boolean existsByUserName(String userName);

	public Boolean existsByEmail(String email);
	
	public Boolean existsByPhoneNumber(String email);

	public List<RegistrationEntity> findByPassword(String password);

	public RegistrationEntity findByEmail(String s);

	public RegistrationEntity findByPhoneNumber(String s);

}
