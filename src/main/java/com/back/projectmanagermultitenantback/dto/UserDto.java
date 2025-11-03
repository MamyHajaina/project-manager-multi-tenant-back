package com.back.projectmanagermultitenantback.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Timestamp createdAt;
    private String role;
    private List<OrganizationMemberDto> organization;
}
