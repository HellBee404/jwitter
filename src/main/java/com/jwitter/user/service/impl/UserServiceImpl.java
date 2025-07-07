package com.jwitter.user.service.impl;

import com.jwitter.user.dto.response.UserResponseDTO;
import com.jwitter.user.entity.User;
import com.jwitter.user.mapper.UserMapper;
import com.jwitter.user.repository.UserRepository;
import com.jwitter.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<User> findAll() {
        log.info("Find all users");
        return userRepository.findAll();
    }

    @Override
    public ResponseEntity<UserResponseDTO> findByUsername(String username) {
        try {
            if (userRepository.findByUsername(username).isPresent()) {
                log.info("User found with username {}", username);
                return ResponseEntity.ok(userMapper.toResponseDTO(userRepository.findByUsername(username).get()));
            }
        } catch (Exception e) {
            log.error("User not found", e);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    @Transactional
    public ResponseEntity<?> createUser(User user) {
        return null;
    }

    @Override
    public ResponseEntity<UserResponseDTO> updateUser(User user) {
        return null;
    }

    @Override
    public ResponseEntity<UserResponseDTO> deleteUserByUsername(String username) {
        try {
            if (userRepository.findByUsername(username).isPresent()) {
                log.info("User found with username {}", username);
                userRepository.delete(userRepository.findByUsername(username).get());
                return ResponseEntity.ok(userMapper.toResponseDTO(userRepository.findByUsername(username).get()));
            }
        } catch (Exception e) {
            log.error("User not found", e);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }
}
