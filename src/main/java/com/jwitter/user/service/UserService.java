package com.jwitter.user.service;

import com.jwitter.user.entity.User;

import java.util.List;

public interface UserService {
    void save(User user);

    List<User> findAll();
}
