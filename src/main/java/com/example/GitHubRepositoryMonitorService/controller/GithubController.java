package com.example.GitHubRepositoryMonitorService.controller;

import com.example.GitHubRepositoryMonitorService.enums.status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import com.example.GitHubRepositoryMonitorService.dtos.RequestDto;
import com.example.GitHubRepositoryMonitorService.dtos.ResponseDto;
import com.example.GitHubRepositoryMonitorService.service.GithubMonitorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/repositories")
public class GithubController {

    @Autowired
    private final GithubMonitorService repositoryMonitorService;

    public GithubController(GithubMonitorService repositoryMonitorService) {
        this.repositoryMonitorService = repositoryMonitorService;
    }


    @PostMapping
    public ResponseEntity<ResponseDto> addRepository(@Valid @RequestBody RequestDto requestDto) {
        ResponseDto responseDto = repositoryMonitorService.addRepository(requestDto);
        return ResponseEntity.ok(responseDto);
    }
    @GetMapping
    public ResponseEntity<Page<ResponseDto>> getAllRepositories ( @PageableDefault(size = 10) Pageable pageable){

     return ResponseEntity.ok(  repositoryMonitorService.getAllRepositories((Pageable) pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRepository(@PathVariable UUID id){

        repositoryMonitorService.deleteRepository(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/refresh")
    public ResponseEntity<ResponseDto> refreshRepository(@PathVariable UUID id,@Valid @RequestBody RequestDto requestDto) {

        ResponseDto responseDto = repositoryMonitorService.updateRepository(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getOneRepository(@PathVariable UUID id) {
        return ResponseEntity.ok(repositoryMonitorService.getOneRepository(id));
    }
    @GetMapping("/filter")
    public ResponseEntity<Page<ResponseDto>> getLanguageAndStatusFilter(
            @RequestParam(required = false) String language,
            @RequestParam(required = false) status status,
            @PageableDefault(size = 10) Pageable pageable)
    {
        if (language != null && status != null) {
            return ResponseEntity.ok(repositoryMonitorService.findByLanguageAndStatus(language, status, pageable));
        }
        return ResponseEntity.ok(repositoryMonitorService.getAllRepositories(pageable));
    }

}
