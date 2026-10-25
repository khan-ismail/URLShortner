package com.zerotoismail.shorturlservice.exception;

public class UrlNotFoundException extends RuntimeException {

    public UrlNotFoundException(String msg) {
        super(msg);
    }
}
