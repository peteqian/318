package com.productgroup.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ProductFailedException extends RuntimeException{

    public ProductFailedException(String msg) {
        super(msg);
    }
}