package com.businessgroup.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductNotFoundException extends NoSuchElementException {
    public ProductNotFoundException(String msg){
        super(msg);
    }
}
