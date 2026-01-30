package com.example.monitor.entity;

import com.example.monitor.enums.Status;
import jakarta.persistence.*;
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
        @UniqueConstraint(columnNames = {"owner", "repo_name"})
})
public class GitHubMonitor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "owner", nullable = false)
    private String owner;

    @Column(name = "repo_name", nullable = false)
    private String repoName;

    @Column(name = "stars", nullable = false)
    private Integer stars;

    @Column(name = "forks", nullable = false)
    private Integer forks;

    @Column(name = "open_issues", nullable = false)
    private Integer openIssues;

    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "last_synced_at", nullable = false)
    private LocalDateTime lastSyncedAt;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Version
    private Integer version;
}