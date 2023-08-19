package com.lancesoft.dto;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.lancesoft.entity.CategoriesEntity;
import com.lancesoft.entity.CustomeIdGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductsDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_sql")
	@GenericGenerator(name = "user_sql", strategy = "com.lancesoft.entity.CustomeIdGenerator", parameters = {
			@Parameter(name = CustomeIdGenerator.INCREMENT_PARAM, value = "1"),
			@Parameter(name = CustomeIdGenerator.VALUE_PREFIX_PARAMAETER, value = "PROD"),
			@Parameter(name = CustomeIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d") })
	private String prodId;
	private String prodName;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date addOnDate;
	private String price;
	private String imageUrl;
	private String qty;
	@ManyToOne
	@JoinColumn(name = "catId")
	private CategoriesEntity categoriesEntity;
	private String status;

}
