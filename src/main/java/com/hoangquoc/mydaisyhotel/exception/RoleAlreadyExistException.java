package com.hoangquoc.mydaisyhotel.exception;

public class RoleAlreadyExistException extends RuntimeException{
    public RoleAlreadyExistException(String message) {
        super(message);
    }
}