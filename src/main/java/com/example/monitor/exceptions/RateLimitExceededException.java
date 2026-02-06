package com.example.monitor.exceptions;

import org.springframework.http.HttpStatus;

public class RateLimitExceededException extends BaseException {

    public RateLimitExceededException() {
        super("Limit aşıldı İşlem Başarısız.",
                HttpStatus.FORBIDDEN);
    }
}