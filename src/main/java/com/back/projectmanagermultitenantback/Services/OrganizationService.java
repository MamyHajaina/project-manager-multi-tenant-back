package com.back.projectmanagermultitenantback.Services;

import com.back.projectmanagermultitenantback.dto.OrganizationCreateDto;
import com.back.projectmanagermultitenantback.dto.OrganizationDto;
import com.back.projectmanagermultitenantback.dto.OrganizationUpdateDto;

import java.util.List;

public interface OrganizationService {
    List<OrganizationDto> findAll();
    OrganizationDto findById(Long id);
    OrganizationDto create(OrganizationCreateDto dto);
    OrganizationDto update(Long id, OrganizationUpdateDto dto);
    void delete(Long id);
}
