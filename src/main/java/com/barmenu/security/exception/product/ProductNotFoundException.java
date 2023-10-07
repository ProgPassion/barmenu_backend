package com.barmenu.security.exception.product;

public class ProductNotFoundException extends Exception {
    @Override
    public String getMessage() {
        return "Product not found!";
    }
}
