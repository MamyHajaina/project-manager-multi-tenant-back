package com.back.projectmanagermultitenantback.Repositories;

import com.back.projectmanagermultitenantback.Models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

}
