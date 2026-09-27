package com.wishit.product.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BackendException.class)
    public ResponseEntity<?> handleBackendException(
            BackendException ex) {

        return ResponseEntity
                .status(ErrorCodes.getStatus(ex.getErrorNo()))
                .body(Map.of(
                        "errorNo", ex.getErrorNo(),
                        "errorMessage", ex.getErrorMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {

        return ResponseEntity
                .status(ErrorCodes.getStatus(
                        ErrorCodes.INTERNAL_ERROR))
                .body(Map.of(
                        "errorNo", ErrorCodes.INTERNAL_ERROR,
                        "errorMessage",
                        ErrorCodes.getMessage(
                                ErrorCodes.INTERNAL_ERROR)
                ));
    }
}