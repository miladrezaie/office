package com.javasampleapproach.jqueryboostraptable.controller;

import com.javasampleapproach.jqueryboostraptable.enums.Authority;
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
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void save(User user) {
        user.setPass(bCryptPasswordEncoder.encode(user.getPass()));
        user.setFullname(user.getFName() + " " + user.getLname());
//        user.setRoles();
        userRepository.save(user);
    }

    @Override
    public void saveuemza(User user, MultipartFile file) {
//        System.out.println("password is a Encode Method: "+ bCryptPasswordEncoder.encode(user.getPass()));
//        System.out.println("password is a : "+ bCryptPasswordEncoder.matches(,user.getPass()));
//        user.setPass(bCryptPasswordEncoder.encode(user.getPass()));
//        user.setFullname(user.getFName() + " " + user.getLname());
//        System.out.println("get role user : "+user.getRoles());
//        System.out.println("new hash role user : "+new HashSet<>(user.getRoles()));
        user.setRoles(new HashSet<>(user.getRoles()));
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
//        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByPersonalId(username);
    }

    @Override
    public long count() {
        return userRepository.count();
    }

}