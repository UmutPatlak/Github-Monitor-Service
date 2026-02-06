package com.example.monitor.dto;

import com.example.monitor.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {

    private UUID id;

    private String owner;

    private String repoName;

    private Integer stars;

    private Integer forks;

    private Integer openIssues;

    private String language;

    private LocalDateTime lastSyncedAt;

    private LocalDateTime createdAt;

    private Status status;

    private Integer trendingScore;
}