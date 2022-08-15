package com.eauction.seller.configuration;

import com.eauction.seller.exception.InvalidRequestException;

import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignCustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

        switch (response.status()){
            case 412:
                return new InvalidRequestException("412");
            case 404:
                return new InvalidRequestException("404");
            default:
                return new Exception("Generic error");
        }
    }
}