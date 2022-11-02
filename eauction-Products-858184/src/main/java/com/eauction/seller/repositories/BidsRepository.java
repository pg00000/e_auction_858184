package com.eauction.seller.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.eauction.seller.model.Bids;

public interface BidsRepository extends CrudRepository<Bids, String>{

	List<Bids> findByProductId(String productId);
	
}
