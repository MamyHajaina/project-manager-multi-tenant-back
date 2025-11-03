package com.back.projectmanagermultitenantback.Mappers;

import com.back.projectmanagermultitenantback.Models.Project;
import com.back.projectmanagermultitenantback.dto.ProjectCreatDto;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    // DTO -> Entity
    public Project toDTO(ProjectCreatDto dto) {
        if (dto == null) return null;
        Project e = new Project();
        e.setName(dto.getName());
        e.setDescription(dto.getDescription());
        e.setStatus(dto.getStatus());
        return e;
    }

    // Entity -> DTO
    public ProjectCreatDto toDTO(Project e) {
        if (e == null) return null;
        ProjectCreatDto dto = new ProjectCreatDto();
        e.setName(dto.getName());
        e.setDescription(dto.getDescription());
        e.setStatus(dto.getStatus());
        return dto;
    }
}
