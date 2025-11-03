package com.back.projectmanagermultitenantback.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrganizationDto {
    private Long id;
    private String slug;
    private String name;
    private Timestamp createdAt;
}
