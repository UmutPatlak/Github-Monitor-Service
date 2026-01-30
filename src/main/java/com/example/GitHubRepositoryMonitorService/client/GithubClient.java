package com.example.GitHubRepositoryMonitorService.client;

import com.example.GitHubRepositoryMonitorService.dtos.GithubResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
@Component
@RequiredArgsConstructor
public class GithubClient {

    private final WebClient webClient;
    public Mono<GithubResponseDto> getRepositoryDetails(String owner, String repoName) {
        return webClient
                .get()
                .uri("/repos/{owner}/{repo}", owner, repoName)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        Mono.error(new RuntimeException("GitHub API hatası!")))
                .bodyToMono(GithubResponseDto.class);
    }
}