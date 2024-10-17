package com.adamur.user_management.exception;

public class UserNotFoundException extends  Exception{
    public UserNotFoundException(String message) {
        super(message);
    }
}
