package com.lancesoft.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="reg")
public class RegistrationEntity {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "user_sql")
	@GenericGenerator(name="user_sql", strategy="com.lancesoft.entity.CustomeIdGenerator", parameters = {
			@Parameter(name=CustomeIdGenerator.INCREMENT_PARAM, value="1"),
			@Parameter(name=CustomeIdGenerator.VALUE_PREFIX_PARAMAETER, value="OMG"),
			@Parameter(name=CustomeIdGenerator.NUMBER_FORMAT_PARAMETER, value="%05d")
	})
	
	private String user_id;
	private String firstName;
	private String lastName;
	private String email;
	private String dateOfBirth;
	private String phoneNumber;
	private String userName;
	private String password;
	
	@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name="user_role", joinColumns = @JoinColumn(name="user_Id"), inverseJoinColumns = @JoinColumn(name="role_id"))
	private List<Authorities> authorities;

}
