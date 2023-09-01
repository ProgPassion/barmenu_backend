package com.barmenu.security.exception.handler;

import com.barmenu.security.category.ErrorDetails;
import com.barmenu.security.exception.category.CategoryIdDoesntExistsException;
import com.barmenu.security.exception.category.CategoryNameExistsException;
import com.barmenu.security.exception.product.ProductNameExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@ResponseBody
public class RestControllerExceptionHandler {

    @ExceptionHandler({CategoryNameExistsException.class, CategoryIdDoesntExistsException.class, ProductNameExistsException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    private ErrorDetails handleBadRequest(Exception e, WebRequest request) {
        return new ErrorDetails(e.getMessage());
    }
}
