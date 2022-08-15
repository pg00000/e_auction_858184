package com.eauction.buyer.bids.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ResponseMessage {
    private Integer statusCode;
    private String status;
    private String id;
    private String message;

}
