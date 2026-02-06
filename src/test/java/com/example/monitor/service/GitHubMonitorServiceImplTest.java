package com.example.monitor.service;

import com.example.monitor.client.GitHubClient;
import com.example.monitor.dto.*;
import com.example.monitor.entity.GitHubMonitor;
import com.example.monitor.exceptions.*;
import com.example.monitor.mapper.GitHubMonitorMapper;
import com.example.monitor.repository.GitHubMonitorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GitHubMonitorServiceImplTest {

    @Mock
    private GitHubMonitorRepository repo;
    @Mock
    private GitHubClient client;
    @Mock
    private GitHubMonitorMapper mapper;

    @InjectMocks
    private GitHubMonitorServiceImpl service;


    @Test
    void add_repository_should_throw_exception_when_already_exists() {
        var request = RequestDto.builder().owner("umut").repoName("repo").build();
        when(repo.existsByOwnerAndRepoName("umut", "repo")).thenReturn(true);

        assertThrows(DuplicateRepositoryException.class, () -> service.addRepository(request));
        verify(repo, never()).save(any());
    }

    @Test
    void get_one_repository_should_return_dto_when_id_exists() {
        var id = UUID.randomUUID();
        var entity = GitHubMonitor.builder().id(id).build();

        when(repo.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.todto(entity)).thenReturn(new ResponseDto());

        var result = service.getOneRepository(id);

        assertNotNull(result);
    }


    @Test
    void delete_repository_should_throw_exception_when_id_not_found() {
        var id = UUID.randomUUID();
        when(repo.existsById(id)).thenReturn(false);

        assertThrows(IdNotFoundException.class, () -> service.deleteRepository(id));
    }


}