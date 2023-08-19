package com.lancesoft.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "reg")
public class RegistrationDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotEmpty(message = "first name should not be empty")
	private String firstName;
	private String lastName;
	@NotEmpty(message = "email should not be empty")
	// @UniqueElements(message = "email already registred")
	@Email(message = "please provide valid email")
	private String email;
	@NotEmpty(message = "date of birth not empty")
	private String dob;
	@NotEmpty(message = "phone number should not be empty")

	private String phoneNumber;

	// @UniqueElements(message = "userName already registred")
	@Email(message = "please provide valid email")
	@Column(unique = true)
	private String userName;
	@Size(min = 8, message = "Ur Password Should contain 8 characters")
	private String password;
	private String userOtp;
}
