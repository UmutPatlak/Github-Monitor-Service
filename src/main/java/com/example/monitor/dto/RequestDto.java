package com.example.monitor.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
@Data
@Builder
public class RequestDto {

    @NotBlank(message = "Owner Boş olamaz")
    String owner;

    @NotBlank(message = "Repository Adı Boş olamaz")
    String repoName;
}