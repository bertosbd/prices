package com.company.prices.infrastructure.api.advice;

import com.company.prices.infrastructure.api.dto.ErrResponse;
import com.company.prices.infrastructure.api.exception.PriceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void givenHandlePriceNotFoundException_ShouldReturnNotFoundStatus() {
        PriceNotFoundException exception = new PriceNotFoundException(1, 1, LocalDateTime.now());

        ResponseEntity<String> response = globalExceptionHandler.handlePriceNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(exception.getMessage(), response.getBody());
    }

    @Test
    void givenHandleArgumentsTypeMismatchException_ShouldReturnBadRequestStatus() {
        MethodArgumentTypeMismatchException exception = new MethodArgumentTypeMismatchException(
                "value", String.class, "param", null, new IllegalArgumentException());

        ResponseEntity<ErrResponse> response = globalExceptionHandler.handleArgumentsTypeMismatchException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("400", response.getBody().getCode());
        assertEquals("Invalid parameter type", response.getBody().getMessage());
    }

    @Test
    void givenHandleMissingServletRequestParameterException_ShouldReturnBadRequestStatus() {
        MissingServletRequestParameterException exception = new MissingServletRequestParameterException("param", "String");

        ResponseEntity<ErrResponse> response = globalExceptionHandler.handleMissingServletRequestParameterException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("400", response.getBody().getCode());
        assertEquals("Missing parameters", response.getBody().getMessage());
    }

    @Test
    void givenHandleUncaughtException_ShouldReturnInternalServerErrorStatus() {
        RuntimeException exception = new RuntimeException("Internal server error");

        ResponseEntity<ErrResponse> response = globalExceptionHandler.handleUncaughtException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("500", response.getBody().getCode());
        assertEquals("Internal server error", response.getBody().getMessage());
    }
}