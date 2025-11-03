package com.back.projectmanagermultitenantback.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserLoginDto {
    private String email;
    private String password;
}
