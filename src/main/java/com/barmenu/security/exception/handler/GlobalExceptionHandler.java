package com.barmenu.security.exception.handler;

import com.barmenu.security.exception.ErrorDetails;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if (ex.getBindingResult().getFieldError() != null) { //return first validation error
            return new ResponseEntity<>(new ErrorDetails(ex.getBindingResult().getFieldError().getDefaultMessage()),
                    HttpStatus.BAD_REQUEST);
        }
        final var errorDetails = new ErrorDetails("Bad request");
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
