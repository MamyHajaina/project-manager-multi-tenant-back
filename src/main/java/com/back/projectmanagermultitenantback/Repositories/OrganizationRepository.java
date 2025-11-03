package com.back.projectmanagermultitenantback.Repositories;

import com.back.projectmanagermultitenantback.Models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    boolean existsBySlug(String slug);
    List<Organization> findAllBySlug(String slug);

    Optional<Organization> findBySlug(String slug);

}
