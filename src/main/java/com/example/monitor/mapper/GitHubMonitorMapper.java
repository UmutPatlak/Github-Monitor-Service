package com.example.monitor.mapper;

import com.example.monitor.dto.ResponseDto;
import com.example.monitor.entity.GitHubMonitor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GitHubMonitorMapper {

    ResponseDto toDto(GitHubMonitor gitHubMonitor);
}