    package com.example.GitHubRepositoryMonitorService.mapper;
    import com.example.GitHubRepositoryMonitorService.dtos.ResponseDto;
    import com.example.GitHubRepositoryMonitorService.entity.GithubMonitor;
    import org.mapstruct.Mapper;


    @Mapper(componentModel = "spring")
    public interface    GithubMonitorMapper {

               ResponseDto toDto(GithubMonitor repositoryMonitor) ;


                    }


