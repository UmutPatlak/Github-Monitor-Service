package com.example.GitHubRepositoryMonitorService.entity;
import com.example.GitHubRepositoryMonitorService.enums.status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "repository_monitor", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"owner", "repo_Name"})})
public class GithubMonitor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id ;
    @Column(name = "owner",nullable = false)
    private String owner;
    @Column(name = "repo_Name",nullable = false)
    private String repoName;
    @Column(name = "stars",nullable = false)
    private Integer stars ;
    @Column(name = "forks",nullable = false)
    private Integer forks;
    @Column(name = "open_Issues",nullable = false)
    private  Integer openIssues;
    @Column(name = "language",nullable = false)
    private  String language;
    @Column(name = "last_Synced_At",nullable = false)
    private LocalDateTime lastSyncedAt ;
    @Column(name = "created_At")
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private status status;
    @Version
    private Integer version;

}
