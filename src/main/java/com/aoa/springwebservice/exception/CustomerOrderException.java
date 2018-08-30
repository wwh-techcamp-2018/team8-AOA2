package com.aoa.springwebservice.exception;

public class CustomerOrderException extends RuntimeException {
    public CustomerOrderException(String message) {
        super(message);
    }
}
