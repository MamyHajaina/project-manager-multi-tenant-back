package com.back.projectmanagermultitenantback.Mappers;

import com.back.projectmanagermultitenantback.Models.User;
import com.back.projectmanagermultitenantback.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // DTO -> Entity
    public User toDTO(UserDto dto) {
        if (dto == null) return null;
        User e = new User();
        e.setId(dto.getId());
        e.setEmail(dto.getEmail());
        // passwordHash non présent dans AppUserDto (sécurité) => laissé null
        e.setCreatedAt(dto.getCreatedAt());
        return e;
    }

    // Entity -> DTO
    public UserDto toDTO(User e) {
        if (e == null) return null;
        UserDto dto = new UserDto();
        dto.setId(e.getId());
        dto.setEmail(e.getEmail());
        dto.setFirstName(e.getFirstName());
        dto.setLastName(e.getLastName());
        dto.setRole(e.getRole() != null ? e.getRole().toString() : null);
        dto.setCreatedAt(e.getCreatedAt());
        return dto;
    }
}
