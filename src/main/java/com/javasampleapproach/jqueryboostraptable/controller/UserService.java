package com.javasampleapproach.jqueryboostraptable.controller;

import org.springframework.web.multipart.MultipartFile;

import com.javasampleapproach.jqueryboostraptable.model.User;

public interface UserService {
    void save(User user , Integer e);
    void saveuemza(User user , Integer e, MultipartFile file);
    User findByUsername(String username);
  
}