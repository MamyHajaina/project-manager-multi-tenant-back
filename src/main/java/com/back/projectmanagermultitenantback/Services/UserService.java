package com.back.projectmanagermultitenantback.Services;

import com.back.projectmanagermultitenantback.dto.UserCreateDto;
import com.back.projectmanagermultitenantback.dto.UserDto;
import com.back.projectmanagermultitenantback.dto.UserUpdateDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserDto> findAll();
    UserDto findById(Long id);
    UserDto create(UserCreateDto dto);
    UserDto update(Long id, UserUpdateDto dto);
    void delete(Long id);
    UserDto login(String email, String password);
}
