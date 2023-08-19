package com.lancesoft.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Data
public class CategoriesEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_sql")
	@GenericGenerator(name = "user_sql", strategy = "com.lancesoft.entity.CustomeIdGenerator", parameters = {
			@Parameter(name = CustomeIdGenerator.INCREMENT_PARAM, value = "1"),
			@Parameter(name = CustomeIdGenerator.VALUE_PREFIX_PARAMAETER, value = "CTG"),
			@Parameter(name = CustomeIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d") })
	private String catId;	
	private String catName;
	

}
