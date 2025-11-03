package com.back.projectmanagermultitenantback.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserUpdateDto {
    @Email @Size(max = 150)
    private String email;

    @Size(min = 8, max = 128)
    private String password; // optionnel
}
