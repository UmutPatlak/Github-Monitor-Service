package com.example.monitor.exceptions;

import org.springframework.http.HttpStatus;

public class IdNotFoundException extends BaseException {
    public IdNotFoundException() {
        super(
                "GitHub Repository Bulunamadı", HttpStatus.NOT_FOUND
        );


    }

}
