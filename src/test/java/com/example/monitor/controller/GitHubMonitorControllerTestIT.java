package com.example.monitor.controller;

import com.example.monitor.dto.ResponseDto;
import com.example.monitor.service.GitHubMonitorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GitHubMonitorController.class)
class GitHubMonitorControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GitHubMonitorService service;

    @Test
    void delete_repository_should_return_no_content() throws Exception {
        var id = UUID.randomUUID();
        doNothing().when(service).deleteRepository(id);

        mockMvc.perform(delete("/api/v1/repositories/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void refresh_repository_should_return_ok() throws Exception {
        var id = UUID.randomUUID();
        var response = ResponseDto.builder().id(id).build();

        when(service.refreshRepository(id)).thenReturn(response);

        mockMvc.perform(post("/api/v1/repositories/{id}/refresh", id))
                .andExpect(status().isOk());
    }
}