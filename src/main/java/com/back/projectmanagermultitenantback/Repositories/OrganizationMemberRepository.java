package com.back.projectmanagermultitenantback.Repositories;

import com.back.projectmanagermultitenantback.Models.Organization;
import com.back.projectmanagermultitenantback.Models.OrganizationMember;
import com.back.projectmanagermultitenantback.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrganizationMemberRepository extends JpaRepository<OrganizationMember, Long> {

    List<OrganizationMember> findByUser_Id(Long userId);

    boolean existsByOrganizationAndUser(Organization organization, User user);

    boolean existsByOrganization_IdAndUser_Id(Long organizationId, Long userId);

    List<OrganizationMember> findByOrganization_Id(Long organizationId);

    @Query("select om.organization.id from OrganizationMember om where om.user.id = :userId")
    List<Long> findOrganizationIdsByUserId(@Param("userId") Long userId);
}
