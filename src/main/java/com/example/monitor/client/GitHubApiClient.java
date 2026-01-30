package com.example.monitor.client;

import com.example.monitor.dto.GitHubResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class GitHubApiClient {

    private final WebClient webClient;

    public Mono<GitHubResponseDto> getRepositoryDetails(String owner, String repoName) {
        return webClient
                .get()
                .uri("/repos/{owner}/{repoName}", owner, repoName)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        Mono.error(new RuntimeException("GitHub API error")))
                .bodyToMono(GitHubResponseDto.class);
    }
}