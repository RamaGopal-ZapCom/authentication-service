package com.zapcom.exception;

/**
 * Created by Rama Gopal
 * Project Name - auth-service
 */
public class UserNotFoundException extends RuntimeException {
    public  UserNotFoundException(String message) {
        super(message);
    }
}
