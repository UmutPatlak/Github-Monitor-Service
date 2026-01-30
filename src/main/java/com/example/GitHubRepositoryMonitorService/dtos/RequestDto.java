package com.example.GitHubRepositoryMonitorService.dtos;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    @NotBlank(message = "owner Boş olamaz")
    private String owner;
    @NotBlank(message = "repoName Boş olamaz")
    private String repoName;



}
