package com.eauction.buyer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BidsEauctionMain {

        public static void main(String[] args) {
            SpringApplication.run(BidsEauctionMain.class, args);
    }

}
