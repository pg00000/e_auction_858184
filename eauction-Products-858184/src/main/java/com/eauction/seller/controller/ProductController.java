package com.eauction.seller.controller;

import static com.eauction.seller.constants.ProductServiceConstants.MEDIA_TYPE_JSON;
import static com.eauction.seller.constants.ProductServiceParams.ADD_PRODUCT;
import static com.eauction.seller.constants.ProductServiceParams.DELETE_API;
import static com.eauction.seller.constants.ProductServiceParams.PRODUCT_SERVICE_PREFIX;
import static com.eauction.seller.constants.ProductServiceParams.SHOW_BIDS;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eauction.seller.Integrator.UserServiceClient;
import com.eauction.seller.exception.InvalidRequestException;
import com.eauction.seller.model.ProductBids;
import com.eauction.seller.model.ProductRequest;
import com.eauction.seller.model.Products;
import com.eauction.seller.model.User;
import com.eauction.seller.services.ProductService;
import com.eauction.seller.validators.ProductValidator;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(PRODUCT_SERVICE_PREFIX)
@Slf4j
public class ProductController {

	@Autowired
	ProductService productService;
	
	@Autowired
	ProductValidator productValidator;
	
	@Autowired
	UserServiceClient userClient;

	@GetMapping("/products")
	public List<Products> getProducts() {
		return productService.getProducts();
	}
	
	@PostMapping(path = ADD_PRODUCT, produces = MEDIA_TYPE_JSON)
	@ResponseStatus(HttpStatus.CREATED)
	public Products addProduct(@RequestBody ProductRequest productInfo) {
		log.info("ProductController >> addProduct");
		
		BeanPropertyBindingResult beanPropertyBindingResult = new BeanPropertyBindingResult(productInfo,
				"Product Validation");

		this.productValidator.validate(productInfo, beanPropertyBindingResult);

		if (beanPropertyBindingResult.hasErrors()) {

			String errorMessage = beanPropertyBindingResult.getAllErrors()
					.stream()
					.map(err -> err.getDefaultMessage())
					.collect(Collectors.joining(" :: "));
			log.error("Product Request Validation exception/s :: {}", errorMessage);
			throw new InvalidRequestException("Product Request Validation exception/s :: " + errorMessage);
		}
		User user = buildUserRequest(productInfo);
		return productService.saveProduct(productInfo, user, productInfo.getEmail());
	}

	@DeleteMapping(path = DELETE_API, produces = MEDIA_TYPE_JSON)
	@ResponseStatus(HttpStatus.OK)
	public String deleteProduct(@PathVariable String productId) {
		log.info("ProductController >> deleteProduct >> {}", productId);
		return productService.deleteProduct(productId);
		
	}
	
	@GetMapping(path = SHOW_BIDS, produces = MEDIA_TYPE_JSON)
	@ResponseStatus(HttpStatus.OK)
	public ProductBids getProductBids(@PathVariable String productId) {
		log.info("ProductController >> getProductBids >> {}", productId);
		return productService.getProductBids(productId);
	}

	private User buildUserRequest(ProductRequest productInfo) {
		log.info("ProductController >> buildUserRequest");
		return User.builder()
				.firstName(productInfo.getFirstName())
				.lastName(productInfo.getLastName())
				.address(productInfo.getAddress())
				.city(productInfo.getCity())
				.state(productInfo.getState())
				.pin(productInfo.getPin())
				.phone(productInfo.getPhone())
				.email(productInfo.getEmail())
				.build();
	}
	
}
