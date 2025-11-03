// com.back.multitenantback.security.SecurityUtils
package com.back.projectmanagermultitenantback.security;

import com.back.projectmanagermultitenantback.Services.OrganizationMemberService;
import com.back.projectmanagermultitenantback.dto.OrganizationMemberDto;
import com.back.projectmanagermultitenantback.dto.UserDto;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public final class SecurityUtils {

    private final OrganizationMemberService organizationMemberService;
    private SecurityUtils(OrganizationMemberService organizationMemberService) {
        this.organizationMemberService = organizationMemberService;
    }

    /** Construit un UserDto "light" depuis le principal sans requête BD. */
    public UserDto currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) throw new AccessDeniedException("Unauthenticated");

        Object principal = auth.getPrincipal();
        if (!(principal instanceof UserPrincipal up)) {
            // fallback: principal = email String (ancien code)
            if (principal instanceof String email) {
                return UserDto.builder()
                        .email(email)
                        .role(firstAuthority(auth))
                        .build();
            }
            throw new AccessDeniedException("Unsupported principal type: " + principal);
        }

        // On remplit id/email/role/orgs (role = premier authority si tu n’en veux qu’un)
        String role = firstAuthority(auth);

        // Ici on met seulement les IDs d’org dans le DTO (tu pourras charger les détails si besoin)
        List<Long> orgIds = up.getOrgIds();

        // Si tu veux absolument un List<OrganizationMemberDto> dans le UserDto, on peut juste
        // mapper les IDs dans des DTO "squelettes" (sans requête DB) :
        List<OrganizationMemberDto> orgMembers = organizationMemberService.findByIdUser(up.getId());

        return UserDto.builder()
                .id(up.getId())
                .email(up.getEmail())
                .role(role)
                .organization(orgMembers) // placeholders (ids seulement via up.getOrgIds)
                .build();
    }

    private static String firstAuthority(Authentication auth) {
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(r -> r != null && !r.isBlank())
                .sorted(Comparator.naturalOrder())
                .findFirst()
                .orElse(null);
    }
}
