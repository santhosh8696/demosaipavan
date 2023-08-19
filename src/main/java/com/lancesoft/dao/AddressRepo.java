package com.lancesoft.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lancesoft.entity.AddressEntity;

public interface AddressRepo extends JpaRepository<AddressEntity, String> {
	
	List<AddressEntity> findByUserName(String userName);
}
