package com.eauction.seller.repositories;

import org.springframework.data.repository.CrudRepository;

import com.eauction.seller.model.Products;

public interface ProductRepository extends CrudRepository<Products, String>{

	Integer deleteByProductId(String productId);

	Products findByProductId(String productId);

}
