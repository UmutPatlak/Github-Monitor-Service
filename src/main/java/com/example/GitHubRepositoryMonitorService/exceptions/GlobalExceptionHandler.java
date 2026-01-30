package com.example.GitHubRepositoryMonitorService.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleIdNotFound(IdNotFoundException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "ID Bulunamadı",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(GithubAccountFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFound(GithubAccountFoundException ex , HttpServletRequest request ){
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Github Hesabı bulunamadı",
                ex.getMessage(),
                request.getRequestURI()
        );
            return  new ResponseEntity<>(error,HttpStatus.NOT_FOUND) ;

    }
    @ExceptionHandler(LanguageAndStatusNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLanguageAndStatusNotFound(LanguageAndStatusNotFoundException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Language ve Status Alanları Boş",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


}

