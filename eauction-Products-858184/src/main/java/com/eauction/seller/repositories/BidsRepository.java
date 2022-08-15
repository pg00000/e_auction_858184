package com.eauction.seller.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eauction.seller.model.Bids;

public interface BidsRepository extends MongoRepository<Bids, String>{

	List<Bids> findByProductId(String productId);
	
}
