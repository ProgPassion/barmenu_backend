package com.barmenu.security.exception.handler;

import com.barmenu.security.exception.ErrorDetails;
import com.barmenu.security.exception.category.CategoryIdDoesntExistsException;
import com.barmenu.security.exception.category.CategoryNameExistsException;
import com.barmenu.security.exception.category.DefaultCategoryException;
import com.barmenu.security.exception.product.ProductNameExistsException;
import com.barmenu.security.exception.product.ProductNotFoundException;
import com.barmenu.security.exception.url.UrlExistsException;
import com.barmenu.security.exception.url.UrlNotFoundException;
import com.barmenu.security.exception.user.UserEmailExistsException;
import com.barmenu.security.exception.user.UserNotFoundException;
import com.barmenu.security.exception.user.WrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@ResponseBody
public class RestControllerExceptionHandler {

    @ExceptionHandler({CategoryNameExistsException.class, CategoryIdDoesntExistsException.class, DefaultCategoryException.class,
            ProductNameExistsException.class, ProductNotFoundException.class, UserEmailExistsException.class,
            UserNotFoundException.class, WrongPasswordException.class, UrlExistsException.class, UrlNotFoundException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    private ErrorDetails handleBadRequest(Exception e, WebRequest request) {
        return new ErrorDetails(e.getMessage());
    }
}
