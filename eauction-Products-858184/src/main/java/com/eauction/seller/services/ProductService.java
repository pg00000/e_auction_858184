package com.eauction.seller.services;

import java.util.List;

import com.eauction.seller.model.ProductBids;
import com.eauction.seller.model.ProductRequest;
import com.eauction.seller.model.Products;
import com.eauction.seller.model.User;

public interface ProductService {
	
	Products getProduct(String productId);
	
	List<Products> getProducts();

	Products saveProduct(ProductRequest productInfo, User user, String sellerId);

	String deleteProduct(String productId);

	ProductBids getProductBids(String productId);

}
