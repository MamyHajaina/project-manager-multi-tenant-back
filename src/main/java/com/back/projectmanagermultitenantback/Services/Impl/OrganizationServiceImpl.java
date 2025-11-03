package com.back.projectmanagermultitenantback.Services.Impl;

import com.back.projectmanagermultitenantback.Mappers.OrganizationMapper;
import com.back.projectmanagermultitenantback.Models.Organization;
import com.back.projectmanagermultitenantback.Repositories.OrganizationRepository;
import com.back.projectmanagermultitenantback.Services.OrganizationService;
import com.back.projectmanagermultitenantback.dto.OrganizationCreateDto;
import com.back.projectmanagermultitenantback.dto.OrganizationDto;
import com.back.projectmanagermultitenantback.dto.OrganizationUpdateDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository repo;
    private final OrganizationMapper mapper;

    public OrganizationServiceImpl(OrganizationRepository repo, OrganizationMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public List<OrganizationDto> findAll() {
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public OrganizationDto findById(Long id) {
        return repo.findById(id).map(mapper::toDTO).orElse(null);
    }

    @Override
    public OrganizationDto create(OrganizationCreateDto dto) {
        Organization e = new Organization();
        e.setSlug(dto.getSlug());
        e.setName(dto.getName());
        return mapper.toDTO(repo.save(e));
    }

    @Override
    public OrganizationDto update(Long id, OrganizationUpdateDto dto) {
        return repo.findById(id).map(e -> {
            if (dto.getSlug() != null) e.setSlug(dto.getSlug());
            if (dto.getName() != null) e.setName(dto.getName());
            return mapper.toDTO(repo.save(e));
        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
