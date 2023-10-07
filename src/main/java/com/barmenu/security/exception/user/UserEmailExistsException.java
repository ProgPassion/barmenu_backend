package com.barmenu.security.exception.user;

public class UserEmailExistsException extends Exception {

    @Override
    public String getMessage() {
        return "User with same email exists!";
    }
}
