package com.eauction.seller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Slf4j
@Document(collection = "bids")
public class Bids implements Serializable {

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
