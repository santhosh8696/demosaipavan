package com.lancesoft.entity;

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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MyCart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_sql")
	@GenericGenerator(name = "user_sql", strategy = "com.lancesoft.entity.CustomeIdGenerator", parameters = {
			@Parameter(name = CustomeIdGenerator.INCREMENT_PARAM, value = "1"),
			@Parameter(name = CustomeIdGenerator.VALUE_PREFIX_PARAMAETER, value = "CART"),
			@Parameter(name = CustomeIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d") })
	private String cartId;
	private String userId;
	
	@ManyToOne
	@JoinColumn(name="prod_id")
	ProductsEntity entity;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date addOnDate;
	private long qty;
}
