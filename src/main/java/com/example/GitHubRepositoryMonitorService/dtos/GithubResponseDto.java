package com.example.GitHubRepositoryMonitorService.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GithubResponseDto {
    @JsonProperty("full_name")
    private String fullName ;
    @JsonProperty("stargazers_count")
    private Integer stars ;
    @JsonProperty("forks_count")
    private Integer forks;
    @JsonProperty("open_issues_count")
    private  Integer openIssues;
    @JsonProperty("language")
    private  String language;
    @JsonProperty("updated_at")
    private LocalDateTime updateAt;
}
