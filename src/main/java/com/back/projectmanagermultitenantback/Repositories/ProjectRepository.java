package com.back.projectmanagermultitenantback.Repositories;

import com.back.projectmanagermultitenantback.Models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
