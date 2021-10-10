package com.javasampleapproach.jqueryboostraptable.controller;

import org.springframework.web.multipart.MultipartFile;

import com.javasampleapproach.jqueryboostraptable.model.User;

public interface UserService {
    void save(User user);

    void saveuemza(User user, MultipartFile file);

    User findByUsername(String username);

}