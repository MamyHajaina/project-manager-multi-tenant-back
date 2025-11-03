package com.back.projectmanagermultitenantback.dto;

import com.back.projectmanagermultitenantback.Models.ProjectStatus;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProjectCreatDto {
    private String name;
    private String description;
    private ProjectStatus status = ProjectStatus.PLANNED;
    private Long organizationID;
}
