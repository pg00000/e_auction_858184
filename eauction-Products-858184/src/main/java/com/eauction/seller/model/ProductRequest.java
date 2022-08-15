package com.eauction.seller.model;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@AllArgsConstructor
//@Setter
//@Getter
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Slf4j
public class ProductRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String _id;

	private String productId;
	
	private String productName;

	private String shortDescription;

	private String detailedDescription;

	private String category;

	private Integer startingPrice;

	private LocalDate bidEndDate;
	
	private String sellerId;
	
	private String firstName;
	
	private String lastName;
	
	private String address;
	
	private String city;
	
	private String state;
	
	private String pin;
	
	private String phone;

	private String email;
	
}
