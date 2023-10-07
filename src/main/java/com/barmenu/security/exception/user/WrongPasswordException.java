package com.barmenu.security.exception.user;

public class WrongPasswordException extends Exception {
    @Override
    public String getMessage() {
        return "Wrong password!";
    }
}
