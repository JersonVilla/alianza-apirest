package com.springboot.alianza.apirest.exception;

public class GeneralException extends RuntimeException{

    public GeneralException() {
    }

    public GeneralException(String message) {
        super(message);
    }
}