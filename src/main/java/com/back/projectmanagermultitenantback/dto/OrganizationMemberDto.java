package com.back.projectmanagermultitenantback.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrganizationMemberDto {
    private Long id;
    private OrganizationDto organization;
    private UserDto user;
}
