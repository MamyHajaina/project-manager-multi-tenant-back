package com.back.projectmanagermultitenantback.Services;

import com.back.projectmanagermultitenantback.Models.Project;

import java.util.List;

public interface ProjectService {
    Project create(Project projectToCreate);
    List<Project> findAll();
}
