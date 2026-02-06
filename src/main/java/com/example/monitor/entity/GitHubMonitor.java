package com.example.monitor.entity;

import com.example.monitor.dto.GitHubResponseDto;
import com.example.monitor.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.Version;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "github_monitors",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"owner", "repo_name"})
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GitHubMonitor {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "owner", nullable = false)
    private String owner;

    @Column(name = "repo_name", nullable = false)
    private String repoName;

    @Column(name = "stars")
    private Integer stars;

    @Column(name = "forks")
    private Integer forks;

    @Column(name = "open_issues")
    private Integer openIssues;

    @Column(name = "language")
    private String language;

    @Column(name = "last_synced_at")
    private LocalDateTime lastSyncedAt;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "trending_score")
    private Integer trendingScore;

    @Column(name = "etag", length = 100)
    private String etag;

    @Version
    @Column(name = "version")
    private Long version;


    public void updateStats(GitHubResponseDto data) {
        int previousStars ;
        if (this.stars != null) {
            previousStars = this.stars;
        } else {
            previousStars = data.getStars();
        }
        this.stars = data.getStars();
        this.forks = data.getForks();
        this.openIssues = data.getOpenIssues();
        this.language = data.getLanguage();
        this.etag = data.getEtag();
        this.status = Status.ACTIVE;
        this.lastSyncedAt = LocalDateTime.now();

        calculateTrendingScore(previousStars);
    }

    private void calculateTrendingScore(int previousStars) {
        this.trendingScore = this.stars - previousStars;


    }
}
