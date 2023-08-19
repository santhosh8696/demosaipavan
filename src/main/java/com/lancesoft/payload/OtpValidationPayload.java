package com.lancesoft.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OtpValidationPayload {
private String otp;
private String password;
}
