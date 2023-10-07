package com.barmenu.security.exception.url;

public class UrlNotFoundException extends Exception {

    @Override
    public String getMessage() {
        return "Url not found";
    }
}
