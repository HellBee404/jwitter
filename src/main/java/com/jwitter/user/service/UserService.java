package com.jwitter.user.service;

import com.jwitter.user.dto.response.UserResponseDTO;
import com.jwitter.user.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    List<User> findAll();

    ResponseEntity<UserResponseDTO> findByUsername(String username);

    ResponseEntity<?> createUser(User user);

    ResponseEntity<UserResponseDTO> updateUser(User user);

    ResponseEntity<UserResponseDTO> deleteUserByUsername(String username);
}
