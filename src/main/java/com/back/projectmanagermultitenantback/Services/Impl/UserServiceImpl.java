package com.back.projectmanagermultitenantback.Services.Impl;

import com.back.projectmanagermultitenantback.Mappers.UserMapper;
import com.back.projectmanagermultitenantback.Models.User;
import com.back.projectmanagermultitenantback.Repositories.UserRepository;
import com.back.projectmanagermultitenantback.Services.UserService;
import com.back.projectmanagermultitenantback.dto.UserCreateDto;
import com.back.projectmanagermultitenantback.dto.UserDto;
import com.back.projectmanagermultitenantback.dto.UserUpdateDto;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder; // si tu n'utilises pas Spring Security, remplace par un stub

    public UserServiceImpl(UserRepository repo, UserMapper mapper, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDto> findAll() {
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public UserDto findById(Long id) {
        return repo.findById(id).map(mapper::toDTO).orElse(null);
    }

    @Override
    public UserDto create(UserCreateDto dto) {
        User e = new User();
        e.setEmail(dto.getEmail());
        e.setPasswordHash(passwordEncoder.encode(dto.getPassword())); // ou dto.getPassword() si pas d'encoder
        return mapper.toDTO(repo.save(e));
    }

    @Override
    public UserDto update(Long id, UserUpdateDto dto) {
        return repo.findById(id).map(e -> {
            if (dto.getEmail() != null) e.setEmail(dto.getEmail());
            if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
                e.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
            }
            return mapper.toDTO(repo.save(e));
        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }


    @Override
    @Transactional(readOnly = true)
    public UserDto login(String email, String password) {
        User user = repo.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        return mapper.toDTO(user);
    }
}
