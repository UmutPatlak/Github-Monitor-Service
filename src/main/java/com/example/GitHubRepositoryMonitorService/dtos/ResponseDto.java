package com.example.GitHubRepositoryMonitorService.dtos;
import com.example.GitHubRepositoryMonitorService.enums.status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    private UUID id ;
    private String owner;
    private String repoName;
    private Integer stars ;
    private Integer forks;
    private  Integer openIssues;
    private  String language;
    private LocalDateTime lastSyncedAt ;
    private LocalDateTime createdAt;
    private status status;


}
