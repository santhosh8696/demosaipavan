package com.lancesoft.customexception;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ControllerException extends RuntimeException {
	
	
	private String errorMessage;
	private String errorCode;

}
