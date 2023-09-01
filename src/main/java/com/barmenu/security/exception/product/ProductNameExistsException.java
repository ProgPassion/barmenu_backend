package com.barmenu.security.exception.product;

public class ProductNameExistsException extends Exception {
    @Override
    public String getMessage() {
        return "Product with same name exists!";
    }
}
