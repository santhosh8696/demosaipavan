package com.lancesoft.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lancesoft.entity.AdminRegistrationEntity;
import com.lancesoft.entity.RegistrationEntity;

@Repository
public interface AdminRegistrationDao extends JpaRepository<AdminRegistrationEntity, Integer> {

	public AdminRegistrationEntity findByUserName(String userName);

	public Boolean existsByUserName(String userName);

	public Boolean existsByEmail(String email);

	public List<RegistrationEntity> findByPassword(String password);

	public AdminRegistrationEntity findByEmail(String s);

	public AdminRegistrationEntity findByPhoneNumber(String s);

}
