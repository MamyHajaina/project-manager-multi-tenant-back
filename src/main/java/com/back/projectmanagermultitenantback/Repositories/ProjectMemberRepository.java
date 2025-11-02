package com.back.projectmanagermultitenantback.Repositories;

import com.back.projectmanagermultitenantback.Models.ProjectMembership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepository extends JpaRepository<ProjectMembership, Long> {
}
