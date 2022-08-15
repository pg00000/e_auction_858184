package com.eauction.buyer.bids.delegate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.eauction.buyer.bids.model.Products;

@FeignClient(name = "product-client", url = "http://localhost:8083")
public interface ProductClient {

    @GetMapping("/e-auction/api/v1/seller/products/{productId}")
    Products getProductData(@PathVariable String productId);
}
