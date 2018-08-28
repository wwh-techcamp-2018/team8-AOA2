package com.aoa.springwebservice.exception;

public class InvalidStateOnStore extends RuntimeException {
    public InvalidStateOnStore(String msg) {
        super(msg);
    }
}
