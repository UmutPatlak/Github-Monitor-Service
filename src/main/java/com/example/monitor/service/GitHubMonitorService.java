package com.example.monitor.service;

import com.example.monitor.dto.RequestDto;
import com.example.monitor.dto.ResponseDto;
import com.example.monitor.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface GitHubMonitorService {
    ResponseDto addRepository(RequestDto request);
    Page<ResponseDto> getAllRepositories(Pageable pageable);
    void deleteRepository(UUID id);
    ResponseDto getOneRepository(UUID id);
    ResponseDto updateRepository(UUID id, RequestDto request);
    Page<ResponseDto> findByLanguageAndStatus(String language, Status status, Pageable pageable);
}