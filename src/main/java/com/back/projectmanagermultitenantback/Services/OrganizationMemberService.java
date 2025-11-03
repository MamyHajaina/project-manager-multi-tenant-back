package com.back.projectmanagermultitenantback.Services;

import com.back.projectmanagermultitenantback.dto.OrganizationMemberDto;

import java.util.List;

public interface OrganizationMemberService {
    List<OrganizationMemberDto> findAll();
    List<OrganizationMemberDto> findByIdUser(Long id);
    List<OrganizationMemberDto> findByIdOrganization(Long id);
    OrganizationMemberDto creat(OrganizationMemberDto dto);
}
