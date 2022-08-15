package com.eauction.buyer.bids.model;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@AllArgsConstructor
//@Setter
//@Getter
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Slf4j
public class BidRequest implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    private String _id;

    private String firstName;

    private String lastName;

    private String address;

    private String city;

    private String state;

    private String pin;

    private String phone;

    private String email;
    
    private String productId;

    private String bidAmount;


}
