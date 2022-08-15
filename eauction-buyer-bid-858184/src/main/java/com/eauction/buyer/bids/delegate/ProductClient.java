package com.eauction.buyer.bids.delegate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.eauction.buyer.bids.model.Products;

@FeignClient(name = "product-client", url = "http://localhost:8081")
public interface ProductClient {

    @GetMapping("/data/{productId}")
    Products getData(@PathVariable String productId);
}
