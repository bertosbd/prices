package com.company.prices.infraestructure.api.advice;

import com.company.prices.infraestructure.api.dto.errors.ErrorDetail;
import com.company.prices.infraestructure.api.dto.errors.ErrorResponse;
import com.company.prices.infraestructure.api.exception.PriceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PriceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handlePriceNotFoundException(PriceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleArgumentsTypeMismatchException(MethodArgumentTypeMismatchException e) {

        ErrorDetail errorDetails = new ErrorDetail(e.getPropertyName(), "Received " +
                e.getValue() + " (" + e.getValue().getClass().getSimpleName() + "). Expected type " + e.getRequiredType().getSimpleName());
        ErrorResponse errorResponse = new ErrorResponse("400", "Invalid parameter type", List.of(errorDetails));

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {

        ErrorDetail errorDetails = new ErrorDetail(e.getParameterName(), e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("400", "Missing parameters", List.of(errorDetails));

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleUncaughtException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse("500", e.getMessage(), List.of());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

