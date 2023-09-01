package com.barmenu.security.exception.category;

public class CategoryIdDoesntExistsException extends Exception {
    @Override
    public String getMessage() {
        return "Category with this id doesn't exists!";
    }
}
