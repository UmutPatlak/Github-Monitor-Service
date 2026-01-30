package com.example.GitHubRepositoryMonitorService.repository;
import com.example.GitHubRepositoryMonitorService.entity.GithubMonitor;
import com.example.GitHubRepositoryMonitorService.enums.status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
@Repository
public interface GithubMonitorRepository extends JpaRepository<GithubMonitor,UUID> {
    Page<GithubMonitor> findAll(Pageable pageable);
    Page<GithubMonitor> findByLanguageAndStatus(String language, status status, Pageable pageable);
    Page<GithubMonitor> findByLanguage(String language, Pageable pageable) ;

    Page<GithubMonitor> findByStatus(status status, Pageable pageable);
}