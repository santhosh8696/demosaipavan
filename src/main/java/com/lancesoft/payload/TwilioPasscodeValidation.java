package com.lancesoft.payload;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Component
public class TwilioPasscodeValidation {
	
	private String otp;
	private String password;
}
