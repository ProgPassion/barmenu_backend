package com.barmenu.security.exception.category;

public class CategoryNameExistsException extends Exception {
    @Override
    public String getMessage() {
        return "Category with same name exists!";
    }
}
