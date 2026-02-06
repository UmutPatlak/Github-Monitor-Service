package com.example.monitor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GitHubResponseDto {

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("stargazers_count")
    private Integer stars;

    @JsonProperty("forks_count")
    private Integer forks;

    @JsonProperty("open_issues_count")
    private Integer openIssues;

    @JsonProperty("language")
    private String language;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    private String etag;
}
