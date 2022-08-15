package com.eauction.buyer.bids.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.eauction.buyer.bids.model.Bids;
import com.eauction.buyer.bids.util.BuyerUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BuyerValidator implements Validator{

	public boolean supports(Class<?> clazz) {

        return Bids.class.isAssignableFrom(clazz);
  }

	@Override
	public void validate(Object target, Errors errors) {
		Bids product = (Bids) target;

		if (StringUtils.isBlank(product.getProductId())) {
			errors.rejectValue("productId", "productId", "productId should not be null");
		}
		
		//1. firstName is not null, min 5 and max 30 characters
		if (StringUtils.isBlank(product.getFirstName())) {
			errors.rejectValue("firstName", "firstName", "firstName Name should not be null");
		}
		if (!BuyerUtil.isValidLength(product.getFirstName(), 5, 30)) {
			errors.rejectValue("firstName", "firstName", "firstName Name should be min 5 and max 30 characters");
		}
		
		//2. lastName is not null, min 3 and max 25 characters.
		if (StringUtils.isBlank(product.getLastName())) {
			errors.rejectValue("lastName", "lastName", "lastName Name should not be null");
		}
		if (!BuyerUtil.isValidLength(product.getLastName(), 5, 25)) {
			errors.rejectValue("lastName", "lastName", "lastName Name should be min 5 and max 30 characters");
		}
		
		//3. email is not null, and it should be valid email pattern, containing a single @.
		if (StringUtils.isBlank(product.getEmail())) {
			errors.rejectValue("email", "email", "email should not be null");
		}
		
		if (!BuyerUtil.isValidEmail(product.getEmail())) {
			errors.rejectValue("email", "email", "email should be a valid");
		}
		
		//4. mobile is not null, min 10 and max 10 character and all must be numeric.
		if (StringUtils.isBlank(product.getPhone())) {
			errors.rejectValue("phone", "phone", "phone Name should not be null");
		}
		if (!BuyerUtil.isValidLength(product.getPhone(), 10, 10)) {
			errors.rejectValue("phone", "phone", "phone Number should be min 10 and max 10 character");
		}
		
	}
}
	