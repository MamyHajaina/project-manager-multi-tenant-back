package com.back.projectmanagermultitenantback.Mappers;

import com.back.projectmanagermultitenantback.Models.Organization;
import com.back.projectmanagermultitenantback.dto.OrganizationDto;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {

    // DTO -> Entity
    public Organization toDTO(OrganizationDto dto) {
        if (dto == null) return null;
        Organization e = new Organization();
        e.setId(dto.getId());
        e.setSlug(dto.getSlug());
        e.setName(dto.getName());
        e.setCreatedAt(dto.getCreatedAt());
        return e;
    }

    // Entity -> DTO
    public OrganizationDto toDTO(Organization e) {
        if (e == null) return null;
        OrganizationDto dto = new OrganizationDto();
        dto.setId(e.getId());
        dto.setSlug(e.getSlug());
        dto.setName(e.getName());
        dto.setCreatedAt(e.getCreatedAt());
        return dto;
    }
}
