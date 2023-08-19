package com.lancesoft.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.lancesoft.entity.CustomeIdGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressDto {

	@Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "user_sql")
    @GenericGenerator(name="user_sql", strategy="com.lancesoft.entity.CustomeIdGenerator", parameters = {
            @Parameter(name=CustomeIdGenerator.INCREMENT_PARAM, value="1"),
            @Parameter(name=CustomeIdGenerator.VALUE_PREFIX_PARAMAETER, value="USER"),
            @Parameter(name=CustomeIdGenerator.NUMBER_FORMAT_PARAMETER, value="%05d")
    })
    private String Id ;
    private String userName;
    private String HouseNo ;
    private String StreetName;
    private String Landmark;
    private String City;
    private String State;
    private String Country;
    private long Pincode;
    private String mobileNumber;
    private String alternateMobileNumber;
    private boolean isDeafultAddress;
}
