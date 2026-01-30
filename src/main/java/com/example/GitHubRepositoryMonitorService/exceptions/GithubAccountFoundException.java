package com.example.GitHubRepositoryMonitorService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class GithubAccountFoundException   extends RuntimeException{


    public GithubAccountFoundException(String message) {
        super(message);

    }
}
