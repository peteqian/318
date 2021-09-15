package com.customergroup.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ContactFailedException extends RuntimeException{

    public ContactFailedException(long contactId) {
        super("The contact " + contactId + " has already been assigned to a customer.");
    }
}