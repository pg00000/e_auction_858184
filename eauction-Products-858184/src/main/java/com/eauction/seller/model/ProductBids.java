package com.eauction.seller.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)

public class ProductBids {

	private Products productDetail;
	
	private List<UserAndBids> userBidsList;

	
}
