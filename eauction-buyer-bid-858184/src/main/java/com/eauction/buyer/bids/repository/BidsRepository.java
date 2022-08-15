package com.eauction.buyer.bids.repository;

import com.eauction.buyer.bids.model.Bids;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BidsRepository extends MongoRepository<Bids, String> {

    public Bids findByProductIdAndBuyerId(String productId, String buyerId);

	List<Bids> findByProductId(String productId);
	
}
