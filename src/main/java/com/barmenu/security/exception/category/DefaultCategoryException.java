package com.barmenu.security.exception.category;

public class DefaultCategoryException extends Exception {
    @Override
    public String getMessage() {
        return "Default category can't be modify or deleted!";
    }
}
