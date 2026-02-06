package com.example.monitor.service;

import com.example.monitor.client.GitHubClient;
import com.example.monitor.dto.GitHubResponseDto;
import com.example.monitor.dto.RequestDto;
import com.example.monitor.dto.ResponseDto;
import com.example.monitor.entity.GitHubMonitor;
import com.example.monitor.enums.Status;
import com.example.monitor.exceptions.DuplicateRepositoryException;
import com.example.monitor.exceptions.IdNotFoundException;
import com.example.monitor.mapper.GitHubMonitorMapper;
import com.example.monitor.repository.GitHubMonitorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GitHubMonitorServiceImpl implements GitHubMonitorService {

    private final GitHubMonitorRepository repository;
    private final GitHubMonitorMapper mapper;
    private final GitHubClient gitHubClient;

    @Override
    public ResponseDto addRepository(RequestDto request) {
        if (repository.existsByOwnerAndRepoName(request.getOwner(), request.getRepoName())) {
            throw new DuplicateRepositoryException();
        }

        GitHubResponseDto clientResponse = gitHubClient.fetchRepository(
                request.getOwner(), request.getRepoName(), null);
        GitHubMonitor entity = mapper.toEntity(request, clientResponse);
        entity.updateStats(clientResponse);
        return mapper.todto(repository.save(entity));
    }

    @Override
    public Page<ResponseDto> getRepositories(String language, Status status, Pageable pageable) {
        Page<GitHubMonitor> page;
        if (language != null && status != null) {
            page = repository.findByLanguageAndStatus(language, status, pageable);
        } else if (language != null) {
            page = repository.findByLanguage(language, pageable);
        } else if (status != null) {
            page = repository.findByStatus(status, pageable);
        } else {
            page = repository.findAll(pageable);
        }
        return page.map(mapper::todto);
    }

    @Override
    public ResponseDto getOneRepository(UUID id) {
        return repository.findById(id)
                .map(mapper::todto)
                .orElseThrow(IdNotFoundException::new);
    }

    @Override
    public void deleteRepository(UUID id) {
        if (!repository.existsById(id)) {
            throw new IdNotFoundException();
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public ResponseDto refreshRepository(UUID id) {
        GitHubMonitor entity = repository.findById(id)
                .orElseThrow(IdNotFoundException::new);
        GitHubResponseDto response = gitHubClient.fetchRepository(
                entity.getOwner(), entity.getRepoName(), entity.getEtag());

        if (response != null) {
            entity.updateStats(response);
            repository.save(entity);
            log.info("Repository güncellendi: {}  ETag : {}", entity.getRepoName()  ,entity.getEtag());
        } else {
            log.info("Değişiklik yok (304), güncelleme yapılmadı.");
        }

        return mapper.todto(entity);
    }

}