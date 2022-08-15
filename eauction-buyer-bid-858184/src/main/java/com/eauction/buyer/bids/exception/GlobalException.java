package com.eauction.buyer.bids.exception;

import static com.eauction.buyer.bids.constants.BuyerServiceConstants.INVALID_INPUT;
import static com.eauction.buyer.bids.constants.BuyerServiceConstants.NOT_FOUND;
import static com.eauction.buyer.bids.constants.BuyerServiceConstants.EXCEPTION;

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
