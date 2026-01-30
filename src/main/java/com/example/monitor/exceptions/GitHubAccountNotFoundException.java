package com.example.monitor.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GitHubAccountNotFoundException extends RuntimeException {

    public GitHubAccountNotFoundException(String message) {
        super(message);
    }
}