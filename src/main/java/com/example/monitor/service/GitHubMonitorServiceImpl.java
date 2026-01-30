package com.example.monitor.service;

import com.example.monitor.client.GitHubApiClient;
import com.example.monitor.dto.GitHubResponseDto;
import com.example.monitor.dto.RequestDto;
import com.example.monitor.dto.ResponseDto;
import com.example.monitor.entity.GitHubMonitor;
import com.example.monitor.enums.Status;
import com.example.monitor.exceptions.GitHubAccountNotFoundException;
import com.example.monitor.exceptions.IdNotFoundException;
import com.example.monitor.exceptions.LanguageAndStatusNotFoundException;
import com.example.monitor.mapper.GitHubMonitorMapper;
import com.example.monitor.repository.GitHubMonitorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class GitHubMonitorServiceImpl  implements GitHubMonitorService{

    private final GitHubMonitorRepository gitHubMonitorRepository;
    private final GitHubMonitorMapper gitHubMonitorMapper;
    private final GitHubApiClient gitHubClient;

    public GitHubMonitorServiceImpl(GitHubMonitorRepository gitHubMonitorRepository,
                                    GitHubMonitorMapper gitHubMonitorMapper,
                                    GitHubApiClient gitHubClient) {
        this.gitHubMonitorRepository = gitHubMonitorRepository;
        this.gitHubMonitorMapper = gitHubMonitorMapper;
        this.gitHubClient = gitHubClient;
    }

    public ResponseDto addRepository(RequestDto request) {
        GitHubResponseDto gitData;
        try {
            gitData = gitHubClient.getRepositoryDetails(request.getOwner(), request.getRepoName()).block();
            if (gitData == null) {
                throw new GitHubAccountNotFoundException("GitHub not found");
            }
        } catch (Exception e) {
            throw new GitHubAccountNotFoundException("Not found" +e.getMessage());
        }

        GitHubMonitor entity = GitHubMonitor.builder()
                .owner(request.getOwner())
                .repoName(request.getRepoName())
                .stars(gitData.getStars())
                .forks(gitData.getForks())
                .openIssues(gitData.getOpenIssues())
                .language(gitData.getLanguage())
                .status(Status.ACTIVE)
                .createdAt(LocalDateTime.now())
                .lastSyncedAt(LocalDateTime.now())
                .build();

        GitHubMonitor saved = gitHubMonitorRepository.save(entity);
        return gitHubMonitorMapper.toDto(saved);
    }

    public Page<ResponseDto> getAllRepositories(Pageable pageable) {
        Page<GitHubMonitor> gitHubMonitorPage = gitHubMonitorRepository.findAll(pageable);
        return gitHubMonitorPage.map(gitHubMonitorMapper::toDto);
    }

    public void deleteRepository(UUID id) {
        if (id == null || !gitHubMonitorRepository.existsById(id)) {
            throw new IdNotFoundException("No ID to delete");
        }
        gitHubMonitorRepository.deleteById(id);
    }

    public ResponseDto getOneRepository(UUID id) {
        GitHubMonitor gitHubMonitor = gitHubMonitorRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("ID not found"));
        return gitHubMonitorMapper.toDto(gitHubMonitor);
    }

    public ResponseDto updateRepository(UUID id, RequestDto request) {
        GitHubMonitor gitHubMonitor = gitHubMonitorRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("ID not found"));

        gitHubMonitor.setOwner(request.getOwner());
        gitHubMonitor.setRepoName(request.getRepoName());

        GitHubResponseDto gitData;
        try {
            gitData = gitHubClient.getRepositoryDetails(
                    gitHubMonitor.getOwner(),
                    gitHubMonitor.getRepoName()
            ).block();

            if (gitData == null) {
                throw new GitHubAccountNotFoundException("GitHub details not found");
            }

            gitHubMonitor.setStars(gitData.getStars());
            gitHubMonitor.setForks(gitData.getForks());
            gitHubMonitor.setOpenIssues(gitData.getOpenIssues());
            gitHubMonitor.setLanguage(gitData.getLanguage());
            gitHubMonitor.setStatus(Status.ACTIVE);
            gitHubMonitor.setLastSyncedAt(LocalDateTime.now());
        } catch (Exception e) {
            gitHubMonitor.setStatus(Status.FAILED);
            gitHubMonitorRepository.save(gitHubMonitor);
            throw new RuntimeException("Update failed: " + e.getMessage());
        }
        GitHubMonitor updatedEntity = gitHubMonitorRepository.save(gitHubMonitor);
        return gitHubMonitorMapper.toDto(updatedEntity);
    }

    public Page<ResponseDto> findByLanguageAndStatus(String language, Status status, Pageable pageable) {
        Page<GitHubMonitor> gitHubMonitors;

        if (language != null && status != null) {
            gitHubMonitors = gitHubMonitorRepository.findByLanguageAndStatus(language, status, pageable);
        } else if (language != null) {
            gitHubMonitors = gitHubMonitorRepository.findByLanguage(language, pageable);
        } else if (status != null) {
            gitHubMonitors = gitHubMonitorRepository.findByStatus(status, pageable);
        } else {
            throw new LanguageAndStatusNotFoundException("Language and Status are empty");
        }

        return gitHubMonitors.map(gitHubMonitorMapper::toDto);
    }
}