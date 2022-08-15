package com.eauction.buyer.bids.service;

import com.eauction.buyer.bids.model.BidRequest;
import com.eauction.buyer.bids.model.Bids;

public interface BidService {

	Bids getBidDetails(String productId, String buyerEmailId);

	void updateBid(String productId, String buyerEmailId, String newBidAmount);

	Bids placeBid(BidRequest bidRequest, String buyerEmailId);
	
}
