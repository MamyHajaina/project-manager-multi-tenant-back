package com.back.projectmanagermultitenantback.Services;

import com.back.projectmanagermultitenantback.Models.ProjectMembership;

import java.util.List;

public interface ProjectMemberService {
    List<ProjectMembership> findAll();
    List<ProjectMembership> findByIdUser(Long id);
    ProjectMembership findById(Long id);
    ProjectMembership create(ProjectMembership dto);
    ProjectMembership update(Long id, ProjectMembership dto);
    void delete(Long id);
    List<ProjectMembership> findByUser_IdAndOrganization_Id(Long userId, Long organizationId);
}
