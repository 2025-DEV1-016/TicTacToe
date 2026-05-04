package com.bnpp.kata.tictactoe.exception;

import com.bnpp.kata.tictactoe.api.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final String VALIDATION_FAILED = "Validation failed";
    public static final String UNEXPECTED_ERROR = "Unexpected error";

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(IllegalArgumentException ex) {
        log.info("IllegalArgumentException: {}", ex.getMessage());
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), List.of());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflict(IllegalStateException ex) {
        log.info("IllegalStateException: {}", ex.getMessage());
        return build(HttpStatus.CONFLICT, ex.getMessage(), List.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(MethodArgumentNotValidException ex) {
        log.info("Method Argument Not Valid Exception: {}", ex.getMessage());
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .toList();

        return build(HttpStatus.BAD_REQUEST, VALIDATION_FAILED, errors);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneric(Exception ex) {
        log.info("Unexpected error: {}", ex.getMessage());
        return build(
                HttpStatus.INTERNAL_SERVER_ERROR,
                UNEXPECTED_ERROR,
                List.of()
        );
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(NoSuchElementException ex) {
        log.info("Game not found: {}", ex.getMessage());
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), List.of());
    }

    private ErrorResponse build(HttpStatus status, String message, List<String> details) {

        ErrorResponse response = new ErrorResponse();
        response.setStatus(status.value());
        response.setErrorDetail(message);
        response.setExtraErrorData(details);

        return response;
    }
}
