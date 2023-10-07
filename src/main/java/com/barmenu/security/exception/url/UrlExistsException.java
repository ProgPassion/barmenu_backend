package com.barmenu.security.exception.url;

public class UrlExistsException extends Exception {

    @Override
    public String getMessage() {
        return "This url code is already reserved";
    }
}
