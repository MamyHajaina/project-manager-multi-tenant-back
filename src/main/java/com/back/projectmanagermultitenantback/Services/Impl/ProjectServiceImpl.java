package com.back.projectmanagermultitenantback.Services.Impl;

import com.back.projectmanagermultitenantback.Mappers.OrganizationMapper;
import com.back.projectmanagermultitenantback.Models.Project;
import com.back.projectmanagermultitenantback.Repositories.ProjectRepository;
import com.back.projectmanagermultitenantback.Services.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repo;

    public ProjectServiceImpl(ProjectRepository repo, OrganizationMapper mapper) {
        this.repo = repo;
    }

    @Override
    public Project create(Project project) {
        return repo.save(project);
    }

    @Override
    public List<Project> findAll() {
        return repo.findAll();
    }
}
