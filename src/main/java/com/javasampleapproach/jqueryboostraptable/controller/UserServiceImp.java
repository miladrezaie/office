package com.javasampleapproach.jqueryboostraptable.controller;

import com.javasampleapproach.jqueryboostraptable.model.User;
import com.javasampleapproach.jqueryboostraptable.repository.RoleRepository;
import com.javasampleapproach.jqueryboostraptable.repository.UserRepository;
import com.javasampleapproach.jqueryboostraptable.controller.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.HashSet;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user, Integer e) {

        user.setPass(bCryptPasswordEncoder.encode(user.getPass()));
        user.setFullname(user.getFName() + " " + user.getLname());
        if (e == 0) {
            user.setRoles(new HashSet<>(roleRepository.findByname("ADMIN")));
        } else {
            user.setRoles(new HashSet<>(roleRepository.findByname("USER")));
        }
        userRepository.save(user);
    }

    @Override
    public void saveuemza(User user, Integer e, MultipartFile file) {
        user.setPass(bCryptPasswordEncoder.encode(user.getPass()));
        user.setFullname(user.getFName() + " " + user.getLname());
        if (e == 0) {
            user.setRoles(new HashSet<>(roleRepository.findByname("ADMIN")));
        } else {
            user.setRoles(new HashSet<>(roleRepository.findByname("USER")));
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            System.out.println("not a valid file");
        }
        try {
            user.setEmza(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException er) {
            System.out.println("file ");
            er.printStackTrace();
        }
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByPersonalId(username);
    }
}