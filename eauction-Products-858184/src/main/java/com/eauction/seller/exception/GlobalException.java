package com.eauction.seller.exception;

import static com.eauction.seller.constants.ProductServiceConstants.EXCEPTION;
import static com.eauction.seller.constants.ProductServiceConstants.INVALID_INPUT;
import static com.eauction.seller.constants.ProductServiceConstants.NOT_FOUND;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(InvalidRequestException.class)
	protected ResponseEntity handleGlobalException(InvalidRequestException e) {
		return ResponseEntity.status(e.getStatus())
				.body(ErrorResponse.builder().errorCode(INVALID_INPUT).ErrorMessage(e.getMessage()).build());
	}
	@ExceptionHandler(ProductNotFoundException.class)
	protected ResponseEntity handleGlobalException(ProductNotFoundException e) {
		return ResponseEntity.status(e.getStatus())
				.body(ErrorResponse.builder().errorCode(NOT_FOUND).ErrorMessage(e.getMessage()).build());
	}
	@ExceptionHandler(Exception.class)
	protected ResponseEntity handleGlobalException(Exception e) {
		return ResponseEntity.badRequest()
				.body(ErrorResponse.builder().errorCode(EXCEPTION).ErrorMessage(e.getMessage()).build());
	}

}
