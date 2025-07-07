package com.jwitter.user.service.impl;

import com.jwitter.user.entity.User;
import com.jwitter.user.mapper.UserMapper;
import com.jwitter.user.repository.UserRepository;
import com.jwitter.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public void save(User user) {
        userMapper.toResponseDTO(userRepository.save(user));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
