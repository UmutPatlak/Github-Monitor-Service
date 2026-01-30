package com.example.monitor.controller;

import com.example.monitor.enums.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import com.example.monitor.dto.RequestDto;
import com.example.monitor.dto.ResponseDto;
import com.example.monitor.service.GitHubMonitorServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/repositories")
public class GitHubMonitorController {
    private final GitHubMonitorServiceImpl gitHubMonitorService;

    public GitHubMonitorController(GitHubMonitorServiceImpl gitHubMonitorService) {
        this.gitHubMonitorService = gitHubMonitorService;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> addRepository(@Valid @RequestBody RequestDto requestDto) {
        ResponseDto responseDto = gitHubMonitorService.addRepository(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<Page<ResponseDto>> getAllRepositories(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(gitHubMonitorService.getAllRepositories(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRepository(@PathVariable UUID id) {
        gitHubMonitorService.deleteRepository(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/refresh")
    public ResponseEntity<ResponseDto> refreshRepository(@PathVariable UUID id, @Valid @RequestBody RequestDto requestDto) {
        ResponseDto responseDto = gitHubMonitorService.updateRepository(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getOneRepository(@PathVariable UUID id) {
        return ResponseEntity.ok(gitHubMonitorService.getOneRepository(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<ResponseDto>> getLanguageAndStatusFilter(
            @RequestParam(required = false) String language,
            @RequestParam(required = false) Status status,
            @PageableDefault(size = 10) Pageable pageable) {
        if (language != null || status != null) {
            return ResponseEntity.ok(gitHubMonitorService.findByLanguageAndStatus(language, status, pageable));
        }
        return ResponseEntity.ok(gitHubMonitorService.getAllRepositories(pageable));
    }
}