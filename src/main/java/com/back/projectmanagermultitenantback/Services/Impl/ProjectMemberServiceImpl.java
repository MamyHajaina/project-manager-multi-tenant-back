package com.back.projectmanagermultitenantback.Services.Impl;

import com.back.projectmanagermultitenantback.Mappers.OrganizationMapper;
import com.back.projectmanagermultitenantback.Models.ProjectMembership;
import com.back.projectmanagermultitenantback.Repositories.ProjectMemberRepository;
import com.back.projectmanagermultitenantback.Services.ProjectMemberService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectMemberRepository repo;

    public ProjectMemberServiceImpl(ProjectMemberRepository repo, OrganizationMapper mapper) {
        this.repo = repo;
    }

    @Override
    public List<ProjectMembership> findAll() {
        return repo.findAll();
    }

    @Override
    public ProjectMembership findById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public ProjectMembership create(ProjectMembership projectMembership) {
        return repo.save(projectMembership);
    }

    @Override
    public ProjectMembership update(Long id, ProjectMembership dto) {
        return null;
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<ProjectMembership> findByIdUser(Long id) {
        return repo.findByUser_Id(id);
    }

    @Override
    public List<ProjectMembership> findByUser_IdAndOrganization_Id(Long userId, Long organizationId) {
        return repo.findByUser_IdAndOrganization_Id(userId, organizationId);
    }
}
