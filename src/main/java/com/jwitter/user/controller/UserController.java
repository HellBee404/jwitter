package com.jwitter.user.controller;

import com.jwitter.user.dto.response.UserResponseDTO;
import com.jwitter.user.entity.User;
import com.jwitter.user.mapper.UserMapper;
import com.jwitter.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUser() {
        List<User> users = userService.findAll();

        return ResponseEntity.ok(userMapper.toListResponseDTO(users));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.ok(userMapper.toResponseDTO(user));
    }
}
