package com.example.monitor.exceptions;

import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorDetails> handleBaseException(BaseException ex) {
        log.error(" hata mesajı: {} | status={}", ex.getMessage(), ex.getHttpStatus());

        return build(
                ex.getHttpStatus(),
                ex.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidation(MethodArgumentNotValidException ex) {

        FieldError error = ex.getBindingResult().getFieldError();

        String message;
        if (error != null) {
            message = error.getField() + ": " + error.getDefaultMessage();
        } else {
            message = "Validation hatası";
        }

        log.warn("Validation hatası: {}", message);

        return build(
                HttpStatus.BAD_REQUEST,
                message
        );
    }



    private ResponseEntity<ErrorDetails> build(
            HttpStatus status,
            String message
    ) {
        ErrorDetails details = ErrorDetails.builder()
                .status(status.value())
                .message(message)
                .build();

        return ResponseEntity.status(status).body(details);
    }

    @Value
    @Builder
    public static class ErrorDetails {
        int status;
        String message;
    }
}
