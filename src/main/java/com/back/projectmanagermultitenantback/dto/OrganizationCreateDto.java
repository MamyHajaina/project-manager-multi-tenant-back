package com.back.projectmanagermultitenantback.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrganizationCreateDto {
    @NotBlank @Size(max = 100)
    private String slug;

    @NotBlank @Size(max = 150)
    private String name;
}
