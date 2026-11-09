package com.zerotoismail.shorturlservice.exception;

import com.zerotoismail.shorturlservice.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * Centralized Global Exception Handler.
 * <p>
 * Intercepts uncaught exceptions across all REST controllers and transforms them
 * into standardized {@link ErrorDto} responses corresponding to the OpenAPI specifications.
 * </p>
 *
 * @author ZeroToIsmail
 * @version 1.0.0
 */
@RestControllerAdvice
public class GlobalException {

    /**
     * Handles {@link UrlNotFoundException} when a requested short URL key does not exist.
     *
     * @param exception the captured {@link UrlNotFoundException}
     * @return {@link ResponseEntity} containing {@link ErrorDto} with HTTP 404 status
     */
    @ExceptionHandler(UrlNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDto> handleUrlNotFoundException(UrlNotFoundException exception) {
        ErrorDto error = ErrorDto.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles validation errors resulting from invalid request bodies annotated with {@code @Valid}.
     *
     * @param exception the captured {@link MethodArgumentNotValidException}
     * @return {@link ResponseEntity} containing {@link ErrorDto} with HTTP 400 status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException exception) {
        String validationErrors = exception.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));

        ErrorDto error = ErrorDto.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(validationErrors.isEmpty() ? "Invalid request payload" : validationErrors)
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link IllegalArgumentException} when an invalid argument is supplied to methods.
     *
     * @param exception the captured {@link IllegalArgumentException}
     * @return {@link ResponseEntity} containing {@link ErrorDto} with HTTP 400 status
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handleIllegalArgumentException(IllegalArgumentException exception) {
        ErrorDto error = ErrorDto.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Fallback handler for all unexpected or unhandled exceptions.
     *
     * @param exception the captured {@link Exception}
     * @return {@link ResponseEntity} containing {@link ErrorDto} with HTTP 500 status
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDto> handleGenericException(Exception exception) {
        ErrorDto error = ErrorDto.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An internal error occurred: " + exception.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
