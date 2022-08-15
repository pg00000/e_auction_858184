package com.eauction.seller.validators;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.eauction.seller.model.Category;
import com.eauction.seller.model.ProductRequest;
import com.eauction.seller.util.ProductUtil;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProductValidator implements Validator{

	public boolean supports(Class<?> clazz) {

        return ProductRequest.class.isAssignableFrom(clazz);

  }

	@Override
	public void validate(Object target, Errors errors) {
		ProductRequest product = (ProductRequest) target;

		// 1. Product Name is not null, min 5 and max 30 characters.
		if (StringUtils.isBlank(product.getProductName())) {
			errors.rejectValue("productName", "productName", "Product Name should not be null");
		}
		if (!ProductUtil.isValidLength(product.getProductName(), 5, 30)) {
			errors.rejectValue("productName", "productName", "Product Name should be min 5 and max 30 characters");
		}
		
		//2.	firstName is not null, min 5 and max 30 characters.
		if (StringUtils.isBlank(product.getFirstName())) {
			errors.rejectValue("firstName", "firstName", "firstName Name should not be null");
		}
		if (!ProductUtil.isValidLength(product.getFirstName(), 5, 30)) {
			errors.rejectValue("firstName", "firstName", "firstName Name should be min 5 and max 30 characters");
		}
		
		//3.	lastName is not null, min 3 and max 25 characters.
		if (StringUtils.isBlank(product.getLastName())) {
			errors.rejectValue("lastName", "lastName", "lastName Name should not be null");
		}
		if (!ProductUtil.isValidLength(product.getLastName(), 5, 25)) {
			errors.rejectValue("lastName", "lastName", "lastName Name should be min 5 and max 30 characters");
		}
		
		//4.	email is not null, and it should be valid email pattern, containing a single @.
		if (StringUtils.isBlank(product.getEmail())) {
			errors.rejectValue("email", "email", "email should not be null");
		}
		
		if (!ProductUtil.isValidEmail(product.getEmail())) {
			errors.rejectValue("email", "email", "email should be a valid");
		}
		
		//5.	mobile is not null, min 10 and max 10 character and all must be numeric.
		if (StringUtils.isBlank(product.getPhone())) {
			errors.rejectValue("phone", "phone", "phone Name should not be null");
		}
		if (!ProductUtil.isValidLength(product.getPhone(), 10, 10)) {
			errors.rejectValue("phone", "phone", "phone Number should be min 10 and max 10 character");
		}
		
		if (!Optional.ofNullable(product.getStartingPrice()).isPresent() || (0 > product.getStartingPrice())) {
			errors.rejectValue("startingPrice", "startingPrice",
					"Starting Price cannot be null or blank or Positive Value ");
		}

		if (null == product.getCategory() || null == Category.getIfPresent(product.getCategory().toUpperCase())) {
			errors.rejectValue("category", "category", "Category is InValid ");
		}

		if (null == product.getBidEndDate() || product.getBidEndDate().isBefore(LocalDate.now())) {
			errors.rejectValue("bidEndDate", "bidEndDate", "Bid End Date cannot be in the past or Null");
		}
	}
}
	