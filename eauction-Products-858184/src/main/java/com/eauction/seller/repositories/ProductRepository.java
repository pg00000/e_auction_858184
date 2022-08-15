package com.eauction.seller.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eauction.seller.model.Products;

public interface ProductRepository extends MongoRepository<Products, String>{

	Integer deleteByProductId(String productId);

	Products findByProductId(String productId);

}
