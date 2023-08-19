package com.lancesoft.customexception;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationCustomException extends RuntimeException  {
	
	
	private String errorCode;
	private String errorMessage;
	

}
