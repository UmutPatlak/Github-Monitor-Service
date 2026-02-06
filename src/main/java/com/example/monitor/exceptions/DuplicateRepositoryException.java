package com.example.monitor.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateRepositoryException extends BaseException {

    public DuplicateRepositoryException() {
        super(
                "Aynı owner ve aynı repo ismi girilemez.",
                HttpStatus.CONFLICT
        );
    }
}