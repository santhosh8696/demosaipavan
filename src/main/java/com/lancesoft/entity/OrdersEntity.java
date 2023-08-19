package com.lancesoft.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@Component
public class OrdersEntity {

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO )
	private int orderId;
	private String userName;
	
	@OneToOne(cascade = CascadeType.ALL)
	AddressEntity addressEntity;
	
	@OneToOne(cascade = CascadeType.ALL)
	MyCartList cartList;
	
	
	String deliveryDate;
	
	String paymentMode;
	
	String amount;
	
	String paymentStatus;
}
