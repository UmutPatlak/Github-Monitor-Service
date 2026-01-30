package com.example.monitor.client;

import com.example.monitor.dto.GitHubResponseDto;
import com.example.monitor.exceptions.GitHubAccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GitHubClient {

    private final WebClient webClient;

    @Retryable(
            retryFor = RuntimeException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public Mono<GitHubResponseDto> getRepository(String owner, String repo) {
        return webClient
                .get()
                .uri("/repos/{owner}/{repo}", owner, repo)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(
                                new GitHubAccountNotFoundException("GitHub client error")))
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> Mono.error(
                                new RuntimeException("GitHub server error")))
                .bodyToMono(GitHubResponseDto.class);
    }

    @Recover
    public Mono<GitHubResponseDto> recover(RuntimeException ex,
                                           String owner,
                                           String repo) {
        return Mono.error(
                new GitHubAccountNotFoundException(
                        "GitHub API failed after retries: " + owner + "/" + repo
                )
        );
    }
}
