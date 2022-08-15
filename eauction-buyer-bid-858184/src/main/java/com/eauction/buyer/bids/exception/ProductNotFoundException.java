package com.eauction.buyer.bids.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends ProductServiceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ProductNotFoundException(String message, Throwable cause) {
		super(HttpStatus.NOT_FOUND,message, cause);
	}
	
	public ProductNotFoundException(String message) {
		super(HttpStatus.NOT_FOUND,message);
	}

}

