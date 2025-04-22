package com.zapcom.exception;

/**
 * Created by Rama Gopal
 * Project Name - auth-service
 */
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}

