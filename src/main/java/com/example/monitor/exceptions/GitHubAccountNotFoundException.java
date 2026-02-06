package com.example.monitor.exceptions;

import org.springframework.http.HttpStatus;

public class GitHubAccountNotFoundException extends BaseException {

    public GitHubAccountNotFoundException() {
        super(
                "GitHub Repository Bulunamadı",HttpStatus.NOT_FOUND
        );
    }
}