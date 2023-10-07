package com.barmenu.security.exception.category;

public class CategoryIdDoesntExistsException extends Exception {
    @Override
    public String getMessage() {
        return "Category doesn't exists!";
    }
}
