package com.eauction.seller.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HttpStatus status;

	public ProductServiceException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}

	public ProductServiceException(HttpStatus status, String message, Throwable cause) {
		super(message, cause);
		this.status = status;
	}

	public ProductServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductServiceException(String message) {
		super(message);
	}
}
