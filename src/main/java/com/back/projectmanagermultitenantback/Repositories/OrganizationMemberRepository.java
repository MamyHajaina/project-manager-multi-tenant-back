package com.back.projectmanagermultitenantback.Repositories;

import com.back.projectmanagermultitenantback.Models.OrganizationMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizationMemberRepository extends JpaRepository<OrganizationMember, Long> {
}
