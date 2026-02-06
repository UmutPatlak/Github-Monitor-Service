package com.example.monitor.repository;

import com.example.monitor.entity.GitHubMonitor;
import com.example.monitor.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GitHubMonitorRepository
        extends JpaRepository<GitHubMonitor, UUID> {

    boolean existsByOwnerAndRepoName(String owner, String repoName);

    Page<GitHubMonitor> findByLanguageAndStatus(
            String language,
            Status status,
            Pageable pageable
    );

    Page<GitHubMonitor> findByLanguage(
            String language,
            Pageable pageable
    );

    Page<GitHubMonitor> findByStatus(
            Status status,
            Pageable pageable
    );
}
