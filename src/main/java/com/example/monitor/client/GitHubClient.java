package com.example.monitor.client;

import com.example.monitor.dto.GitHubResponseDto;
import com.example.monitor.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class GitHubClient {
    private final WebClient webClient;

    public GitHubResponseDto fetchRepository(String owner, String repo, String etag) {
        return webClient.get()
                .uri("/repos/{owner}/{repo}", owner, repo)
                .headers(h -> {
                    if (etag != null) h.setIfNoneMatch(etag);
                })
                .exchangeToMono(response -> {
                    if (response.statusCode() == HttpStatus.NOT_MODIFIED) {
                        return Mono.empty();
                    }

                    if (response.statusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new GitHubAccountNotFoundException());
                    }

                    return response.bodyToMono(GitHubResponseDto.class)
                            .map(dto -> {
                                String newEtag = response.headers().asHttpHeaders().getETag();
                                dto.setEtag(newEtag);
                                return dto;
                            });
                })
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .block(Duration.ofSeconds(10));
    }
}