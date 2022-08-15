package com.eauction.seller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class InvalidRequestException extends ProductServiceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidRequestException(String message, Throwable cause) {
		super(HttpStatus.PRECONDITION_FAILED,message, cause);
	}
	
	public InvalidRequestException(String message) {
		super(HttpStatus.PRECONDITION_FAILED,message);
	}

}
