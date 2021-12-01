package com.javasampleapproach.jqueryboostraptable.controller;

import com.javasampleapproach.jqueryboostraptable.enums.Authority;
import org.springframework.web.multipart.MultipartFile;

import com.javasampleapproach.jqueryboostraptable.model.User;

import java.util.List;

public interface UserService {

    void save(User user);

    void saveuemza(User user, MultipartFile file);

    User findByUsername(String username);


}