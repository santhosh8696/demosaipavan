package com.lancesoft.payload;

import lombok.Data;
import lombok.NoArgsConstructor;
@ NoArgsConstructor
public @Data class  ForgotPasswordPayload {
	private String userName;
	private String oldPassword;
	private String newPassword;
}
