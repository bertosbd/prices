package com.company.prices.infrastructure.api.advice;

import com.company.prices.infrastructure.api.dto.ErrResponse;
import com.company.prices.infrastructure.api.dto.ErrorDetail;
import com.company.prices.infrastructure.api.exception.PriceNotFoundException;
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
    public ResponseEntity<ErrResponse> handleArgumentsTypeMismatchException(MethodArgumentTypeMismatchException e) {

        ErrorDetail errorDetails = new ErrorDetail(e.getPropertyName(), "Received " +
                e.getValue() + " (" + e.getValue().getClass().getSimpleName() + "). Expected type " + e.getRequiredType().getSimpleName());
        ErrResponse ErrResponse = new ErrResponse("400", "Invalid parameter type", List.of(errorDetails));

        return ResponseEntity.badRequest().body(ErrResponse);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {

        ErrorDetail errorDetails = new ErrorDetail(e.getParameterName(), e.getMessage());
        ErrResponse ErrResponse = new ErrResponse("400", "Missing parameters", List.of(errorDetails));

        return ResponseEntity.badRequest().body(ErrResponse);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrResponse> handleUncaughtException(Exception e) {
        ErrResponse ErrResponse = new ErrResponse("500", e.getMessage(), List.of());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrResponse);
    }
}

