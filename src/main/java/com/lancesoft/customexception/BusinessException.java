package com.lancesoft.customexception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessException extends RuntimeException{
	
	
	private String errorMessage;
	private String errorCode;

}
