package com.back.projectmanagermultitenantback.config;

import com.back.projectmanagermultitenantback.Models.Organization;
import com.back.projectmanagermultitenantback.Models.OrganizationMember;
import com.back.projectmanagermultitenantback.Models.User;
import com.back.projectmanagermultitenantback.Models.UserRole;
import com.back.projectmanagermultitenantback.Repositories.OrganizationMemberRepository;
import com.back.projectmanagermultitenantback.Repositories.OrganizationRepository;
import com.back.projectmanagermultitenantback.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private static final String SEED_PASSWORD = "2025Test!?";

    @Bean
    CommandLineRunner seedData(OrganizationRepository organizationRepository,
                               UserRepository userRepository,
                               OrganizationMemberRepository membershipRepository) {
        return args -> {
            PasswordEncoder encoder = new BCryptPasswordEncoder();

            // 1) Organizations (idempotent)
            List<Organization> orgSeeds = List.of(
                    Organization.builder().slug("acme").name("ACME Inc.").build(),
                    Organization.builder().slug("globex").name("Globex Corporation").build(),
                    Organization.builder().slug("initech").name("Initech").build(),
                    Organization.builder().slug("umbrella").name("Umbrella Group").build(),
                    Organization.builder().slug("wayne").name("Wayne Enterprises").build()
            );
            for (Organization o : orgSeeds) {
                if (!organizationRepository.existsBySlug(o.getSlug())) {
                    organizationRepository.save(o);
                    log.info("Created org: {} ({})", o.getName(), o.getSlug());
                }
            }
            Map<String, Organization> orgBySlug = orgSeeds.stream()
                    .map(Organization::getSlug)
                    .collect(Collectors.toMap(
                            slug -> slug,
                            slug -> organizationRepository.findBySlug(slug)
                                    .orElseThrow(() -> new IllegalStateException("Org not found after seed: " + slug))
                    ));


            // 2) Users (25) — Admins sans organisation, autres avec membership
            record U(String f, String l, String email, UserRole role, String orgSlugOrNull) {}

            List<U> users = Stream.of(
                    // Admins (sans org)
                    new U("Super", "Admin", "super.admin@system.local", UserRole.ADMIN, null),
                    new U("Victor", "Admin", "victor.admin@wayne.com", UserRole.ADMIN, null),
                    new U("Heidi", "Admin", "heidi.admin@globex.com", UserRole.ADMIN, null),
                    new U("Ivy", "Admin", "ivy.admin@initech.com", UserRole.ADMIN, null),
                    new U("Quinn", "Admin", "quinn.admin@umbrella.com", UserRole.ADMIN, null),

                    // Client
                    new U("Alice", "Client", "alice.client@acme.com", UserRole.CLIENT, "acme"),
                    new U("Bob", "Client", "bob.client@acme.com", UserRole.CLIENT, "acme"),
                    new U("Carol", "Client", "carol.client@acme.com", UserRole.CLIENT, "acme"),
                    new U("Dave", "Client", "dave.client@acme.com", UserRole.CLIENT, "acme"),
                    new U("Eve", "Client", "eve.client@acme.com", UserRole.CLIENT, "acme")
            ).toList();

            // 3) Insert users + memberships (idempotent)
            for (U u : users) {
                User user = userRepository.findByEmail(u.email).orElse(null);
                if (user == null) {
                    user = User.builder()
                            .firstName(u.f)
                            .lastName(u.l)
                            .email(u.email)
                            .passwordHash(encoder.encode(SEED_PASSWORD))
                            .role(u.role)
                            .build();
                    userRepository.save(user);
                    log.info("Created user: {} {} ({}) - {}", u.f, u.l, u.email, u.role);
                }

                // Si l'user a une org (non-admin), créer/assurer le membership
                if (u.orgSlugOrNull != null) {
                    Organization org = orgBySlug.get(u.orgSlugOrNull);
                    if (!membershipRepository.existsByOrganizationAndUser(org, user)) {
                        OrganizationMember m = OrganizationMember.builder()
                                .organization(org)
                                .user(user)
                                .build();
                        membershipRepository.save(m);
                        log.info("Linked {} to org {}", u.email, u.orgSlugOrNull);
                    }
                }
            }

            log.info("✔ Seed done. orgs={}, users={}, memberships={}",
                    organizationRepository.count(),
                    userRepository.count(),
                    membershipRepository.count());
        };
    }
}
