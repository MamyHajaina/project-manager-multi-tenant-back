package com.back.projectmanagermultitenantback.Services.Impl;

import com.back.projectmanagermultitenantback.Mappers.OrganizationMemberMapper;
import com.back.projectmanagermultitenantback.Repositories.OrganizationMemberRepository;
import com.back.projectmanagermultitenantback.Services.OrganizationMemberService;
import com.back.projectmanagermultitenantback.dto.OrganizationMemberDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationMemberServiceImpl implements OrganizationMemberService {

    private final OrganizationMemberRepository repo;
    private final OrganizationMemberMapper mapper;

    public OrganizationMemberServiceImpl(OrganizationMemberRepository repo, OrganizationMemberMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public List<OrganizationMemberDto> findAll() {
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public List<OrganizationMemberDto> findByIdUser(Long id) {
        return repo.findByUser_Id(id).stream().map(mapper::toDTO).toList();
    }

    @Override
    public List<OrganizationMemberDto> findByIdOrganization(Long id) {
        return repo.findByOrganization_Id(id).stream().map(mapper::toDTO).toList();
    }

    @Override
    public OrganizationMemberDto creat(OrganizationMemberDto dto) {
        return mapper.toDTO(repo.save(mapper.toDTO(dto)));
    }

}
