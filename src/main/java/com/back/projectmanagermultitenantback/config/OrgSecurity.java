package com.back.projectmanagermultitenantback.config;

import com.back.projectmanagermultitenantback.Repositories.OrganizationMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("orgSecurity")
@RequiredArgsConstructor
public class OrgSecurity {
    private final OrganizationMemberRepository orgMemberRepo;

    public boolean hasMembership(Long userId, Long orgId) {
        return orgMemberRepo.existsByOrganization_IdAndUser_Id(userId, orgId);
    }
}

