package com.eauction.buyer.bids.delegate;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.eauction.buyer.bids.model.Products;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceDelegate {
	
    @Autowired
    private ProductClient productClient;

	@HystrixCommand(fallbackMethod = "callProductServiceAndGetData_Fallback")
	public String callProductServiceAndGetData(String productId) {
		log.info("ProductServiceDelegate >> callProductServiceAndGetData >> productId : {}", productId);
		Products product = productClient.getData(productId);
		
		log.info("Response Received as " + product + " -  " + new Date());

		return "NORMAL FLOW !!! - School Name -  " + productId + " :::  Product Details " + product + " -  "
				+ new Date();
	}

	@SuppressWarnings("unused")
	private String callProductServiceAndGetData_Fallback(String schoolname) {
		log.info("Product Service is down!!! fallback route enabled...");
		return "CIRCUIT BREAKER ENABLED!!!No Response From Product Service at this moment. Service will be back shortly - "
				+ new Date();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
