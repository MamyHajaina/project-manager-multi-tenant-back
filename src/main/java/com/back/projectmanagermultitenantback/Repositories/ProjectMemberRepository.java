package com.back.projectmanagermultitenantback.Repositories;

import com.back.projectmanagermultitenantback.Models.ProjectMembership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectMemberRepository extends JpaRepository<ProjectMembership, Long> {

    List<ProjectMembership> findByUser_Id(Long userId);
    List<ProjectMembership> findByUser_IdAndOrganization_Id(Long userId, Long organizationId);
}
