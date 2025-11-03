package com.back.projectmanagermultitenantback.dto;

import com.back.projectmanagermultitenantback.Models.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserCreateDto {
    private String firstName;
    private String lastName;
    @Email @NotBlank @Size(max = 150)
    private String email;
    @NotBlank @Size(min = 8, max = 128)
    private String password; // sera hashé côté service
    private UserRole role;
}
