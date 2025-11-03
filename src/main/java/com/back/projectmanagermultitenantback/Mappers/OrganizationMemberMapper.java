package com.back.projectmanagermultitenantback.Mappers;

import com.back.projectmanagermultitenantback.Models.OrganizationMember;
import com.back.projectmanagermultitenantback.dto.OrganizationMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrganizationMemberMapper {
    private final OrganizationMapper organizationMapper;
    private final UserMapper userMapper;

    // DTO -> Entity
    public OrganizationMember toDTO(OrganizationMemberDto dto) {
        if (dto == null) return null;
        OrganizationMember e = new OrganizationMember();
        e.setId(dto.getId());
        e.setOrganization(organizationMapper.toDTO(dto.getOrganization()));
        e.setUser(userMapper.toDTO(dto.getUser()));
        return e;
    }

    // Entity -> DTO
    public OrganizationMemberDto toDTO(OrganizationMember e) {
        if (e == null) return null;
        OrganizationMemberDto dto = new OrganizationMemberDto();
        dto.setId(e.getId());
        dto.setOrganization(organizationMapper.toDTO(e.getOrganization()));
        dto.setUser(userMapper.toDTO(e.getUser()));
        return dto;
    }
}
