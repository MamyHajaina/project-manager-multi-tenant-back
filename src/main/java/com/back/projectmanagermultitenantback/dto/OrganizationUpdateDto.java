package com.back.projectmanagermultitenantback.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrganizationUpdateDto {
    @Size(max = 100)
    private String slug; // optionnel

    @Size(max = 150)
    private String name; // optionnel
}
