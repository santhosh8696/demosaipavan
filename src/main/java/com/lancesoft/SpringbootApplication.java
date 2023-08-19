package com.lancesoft;


import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.lancesoft.dao.OrdersRepo;
import com.lancesoft.entity.OrdersEntity;



@SpringBootApplication
public class SpringbootApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
		
		
	}

	
	
	@Bean
	public BCryptPasswordEncoder encoder() {

		return new BCryptPasswordEncoder();
	}

	@Bean
	public SimpleMailMessage simpleMail() {
		return new SimpleMailMessage();
	}

}
