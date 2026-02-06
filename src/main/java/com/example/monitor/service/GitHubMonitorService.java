package com.example.monitor.service;

import com.example.monitor.dto.RequestDto;
import com.example.monitor.dto.ResponseDto;
import com.example.monitor.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface GitHubMonitorService {
    ResponseDto addRepository(RequestDto request);
    Page<ResponseDto> getRepositories(String language, Status status, Pageable pageable);
    ResponseDto getOneRepository(UUID id);
    ResponseDto refreshRepository(UUID id);
    void deleteRepository(UUID id);
}