package com.lancesoft.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Authorities {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer role_id;
	private String role;
	

}
