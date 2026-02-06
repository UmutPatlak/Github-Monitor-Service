package com.example.monitor.controller;

import com.example.monitor.dto.RequestDto;
import com.example.monitor.dto.ResponseDto;
import com.example.monitor.enums.Status;
import com.example.monitor.service.GitHubMonitorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/repositories")
@RequiredArgsConstructor
public class GitHubMonitorController {

    private final GitHubMonitorService service;

    @PostMapping
    public ResponseEntity<ResponseDto> addRepository(
            @Valid @RequestBody RequestDto requestDto
    ) {
        ResponseDto responseDto = service.addRepository(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getOneRepository(@PathVariable UUID id) {
        ResponseDto oneRepository = service.getOneRepository(id);
        return ResponseEntity.ok(oneRepository);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRepository(@PathVariable UUID id) {
        service.deleteRepository(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/refresh")
    public ResponseEntity<ResponseDto> refreshRepository(@PathVariable UUID id) {
        ResponseDto responseDto = service.refreshRepository(id);
        return ResponseEntity.ok(responseDto);
    }




    @GetMapping
    public ResponseEntity<Page<ResponseDto>> getRepositories(
            @RequestParam(required = false) String language,
            @RequestParam(required = false) Status status,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        Page<ResponseDto> repositories = service.getRepositories(language, status, pageable);
        return ResponseEntity.ok(repositories);
    }
}