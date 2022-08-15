package com.eauction.buyer.bids.controller;

import static com.eauction.buyer.bids.constants.BuyerServiceParams.PLACE_BID;
import static com.eauction.buyer.bids.constants.BuyerServiceParams.PRODUCT_SERVICE_PREFIX;
import static com.eauction.buyer.bids.constants.BuyerServiceParams.UPDATE_BID;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eauction.buyer.bids.delegate.ProductServiceDelegate;
import com.eauction.buyer.bids.exception.InvalidRequestException;
import com.eauction.buyer.bids.model.BidRequest;
import com.eauction.buyer.bids.model.Bids;
import com.eauction.buyer.bids.model.ResponseMessage;
import com.eauction.buyer.bids.service.BidServiceImpl;
import com.eauction.buyer.bids.validator.BuyerValidator;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(PRODUCT_SERVICE_PREFIX)
@Slf4j
public class BidsController {

    @Autowired 
    BidServiceImpl bidService;
    
    @Autowired 
    BuyerValidator buyerValidator;
    
	@Autowired
	ProductServiceDelegate productServiceDelegate;

	@GetMapping
	public List<Bids> getAll() {
		return bidService.list();
	}

	@ApiOperation(value = "Place the bids")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") 
	})
	@PostMapping(PLACE_BID)
	public ResponseEntity<ResponseMessage> createBid(@RequestBody @Valid BidRequest bidRequest) throws Exception {
		log.info("BidsController >> createBid >> bids : {}", bidRequest);

		BeanPropertyBindingResult beanPropertyBindingResult = new BeanPropertyBindingResult(bidRequest,
				"BidRequest Validation");

		this.buyerValidator.validate(bidRequest, beanPropertyBindingResult);

		if (beanPropertyBindingResult.hasErrors()) {

			String errorMessage = beanPropertyBindingResult.getAllErrors()
					.stream()
					.map(ObjectError::getDefaultMessage)
					.collect(Collectors.joining(" :: "));
			log.error("BidRequest Request Validation exception/s :: {}", errorMessage);
			throw new InvalidRequestException("BidRequest Request Validation exception/s :: " + errorMessage);
		}

		Bids bid = bidService.placeBid(bidRequest, bidRequest.getEmail());

		return ResponseEntity.created(URI.create("/e-auction/api/v1/buyer"))
				.body(this.getResponse(bid.get_id(), "Bid was created succesfully.."));
	}
    
	@ApiOperation(value = "Updates the bids")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") 
	})
	@PutMapping(UPDATE_BID)
	public ResponseEntity<ResponseMessage> updateCustomer(@PathVariable @NotBlank String productId,
			@PathVariable @NotBlank String buyerEmailId, @PathVariable @NotBlank String newBidAmount) {
		log.info("BidsController >> updateCustomer");
		
		if (StringUtils.isBlank(productId)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(this.getResponse("productId", "productId should not be null or blank"));
		}
		if (StringUtils.isBlank(buyerEmailId)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(this.getResponse("buyerEmailId", "buyerEmailId should not be null or blank"));
		}
		if (StringUtils.isBlank(newBidAmount)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(this.getResponse("newBidAmount", "newBidAmount should not be null or blank"));
		}
		
		bidService.updateBid(productId, buyerEmailId, newBidAmount);

		return ResponseEntity.ok().body(this.getResponse(productId, "Bids Updated"));
	}
	
	@GetMapping("/getProductDetails/{productId}")
	public String getStudents(@PathVariable String productId) {
		log.info("Going to call product service to get data!");
		return productServiceDelegate.callProductServiceAndGetData(productId);
	}

    private ResponseMessage getResponse(String id, String message) {
        ResponseMessage response = new ResponseMessage();
        response.setId(id);
        response.setStatus(HttpStatus.OK.name());
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage(message);
        return response;
    }

    private ResponseMessage getErrorResponse(String id, String message) {
        ResponseMessage response = new ResponseMessage();
        response.setId(id);
        response.setStatus(HttpStatus.BAD_REQUEST.name());
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage(message);
        return response;
    }

}