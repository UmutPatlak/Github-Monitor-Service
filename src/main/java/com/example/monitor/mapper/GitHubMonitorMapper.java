    package com.example.monitor.mapper;

    import com.example.monitor.dto.GitHubResponseDto;
    import com.example.monitor.dto.RequestDto;
    import com.example.monitor.entity.GitHubMonitor;
    import com.example.monitor.dto.ResponseDto;
    import org.mapstruct.Mapper;
    import org.mapstruct.Mapping;

    @Mapper(componentModel = "spring")
    public interface GitHubMonitorMapper {

        ResponseDto todto(GitHubMonitor entity);

        GitHubMonitor toEntity(RequestDto requestDto, GitHubResponseDto responseDto);

    }