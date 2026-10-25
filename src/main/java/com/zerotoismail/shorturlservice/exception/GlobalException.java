package com.zerotoismail.shorturlservice.exception;

import com.zerotoismail.shorturlservice.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<ErrorDto> urlNotFoundException(UrlNotFoundException exception) {
        return new ResponseEntity<>(ErrorDto.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .timestamp(System.currentTimeMillis()).build(),
                HttpStatus.NOT_FOUND);
    }
}
