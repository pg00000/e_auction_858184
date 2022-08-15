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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eauction.seller.exception.InvalidRequestException;
import com.eauction.seller.model.ProductBids;
import com.eauction.seller.model.ProductRequest;
import com.eauction.seller.model.Products;
import com.eauction.seller.model.User;
import com.eauction.seller.services.ProductService;
import com.eauction.seller.validators.ProductValidator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(PRODUCT_SERVICE_PREFIX)
@Slf4j
@Api(value="eauction", description="Operations pertaining to products in Online Store")
public class ProductController {

	@Autowired
	ProductService productService;
	
	@Autowired
	ProductValidator productValidator;
	
	@GetMapping("/products")
	public List<Products> getProducts() {
		return productService.getProducts();
	}
	
    @ApiOperation(value = "Adds a product", response = Products.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") 
	})
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
					.map(ObjectError::getDefaultMessage)
					.collect(Collectors.joining(" :: "));
			log.error("Product Request Validation exception/s :: {}", errorMessage);
			throw new InvalidRequestException("Product Request Validation exception/s :: " + errorMessage);
		}
		User user = buildUserRequest(productInfo);
		return productService.saveProduct(productInfo, user, productInfo.getEmail());
	}

    @ApiOperation(value = "Deletes a product")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") 
	})
	@DeleteMapping(path = DELETE_API, produces = MEDIA_TYPE_JSON)
	@ResponseStatus(HttpStatus.OK)
	public String deleteProduct(@PathVariable String productId) {
		log.info("ProductController >> deleteProduct >> {}", productId);
		return productService.deleteProduct(productId);
		
	}
	
    @ApiOperation(value = "Shows the bids")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") 
	})
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
