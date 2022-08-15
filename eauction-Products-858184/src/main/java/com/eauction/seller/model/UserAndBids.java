package com.eauction.seller.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UserAndBids {
	
	private Double bidAmount;
	
	private String userName;

	private String userEmail;
	
	private String phone;
	
}
