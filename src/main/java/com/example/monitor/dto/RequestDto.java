package com.example.monitor.dto;

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

    @NotBlank(message = "Owner cannot be blank")
    private String owner;

    @NotBlank(message = "Repository name cannot be blank")
    private String repoName;
}