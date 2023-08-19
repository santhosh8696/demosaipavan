package com.lancesoft.customexception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
public class MyCustomConfig extends ResponseEntityExceptionHandler{
	
	
	
	
	@ExceptionHandler(RegistrationCustomException.class)
	public List<RegistrationCustomException> throwException(RegistrationCustomException registrationCustomException)
	{
		
		return  (List<RegistrationCustomException>) registrationCustomException;
	}
	
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		
		//System.out.println("kkkkkkkkkkk");
		Map<String,String> map= new HashMap<String, String>();
		
		ex.getBindingResult().getAllErrors().forEach((err)->
		
				{
					
					String fieldError=((FieldError)err).getField();
					
					String message=err.getDefaultMessage();
					
					
					
					map.put(fieldError, message);
					
					
				});
		
		
		return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
	}

}
