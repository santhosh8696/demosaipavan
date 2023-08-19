package com.lancesoft.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TwilioPasscode {

	private String phoneNumber;
}
