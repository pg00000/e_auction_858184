package com.eauction.buyer.bids.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eauction.buyer.bids.model.Products;


public interface ProductRepository extends MongoRepository<Products, String>{

	Products findByProductId(String productId);

}
