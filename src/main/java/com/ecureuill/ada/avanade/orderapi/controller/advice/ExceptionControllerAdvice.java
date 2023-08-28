package com.ecureuill.ada.avanade.orderapi.controller.advice;

import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ecureuill.ada.avanade.orderapi.exceptions.InsufficientStockException;
import com.ecureuill.ada.avanade.orderapi.exceptions.NotFoundException;
import com.ecureuill.ada.avanade.orderapi.exceptions.UniqueKeyException;
import com.ecureuill.ada.avanade.orderapi.exceptions.ForbiddenException;

import jakarta.validation.ValidationException;

@RestControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler{

    @ExceptionHandler({
        NotFoundException.class,
        InsufficientStockException.class,
        UniqueKeyException.class,
        ValidationException.class,
        Exception.class
    })
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }


    @ExceptionHandler({AuthenticationException.class, BadCredentialsException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body(e.getMessage());
    }

    
    @ExceptionHandler({AccessDeniedException.class, ForbiddenException.class})
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(e.getMessage());
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
}
    

