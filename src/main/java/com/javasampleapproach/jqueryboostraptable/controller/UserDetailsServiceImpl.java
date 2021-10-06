package com.javasampleapproach.jqueryboostraptable.controller;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javasampleapproach.jqueryboostraptable.model.User;
import com.javasampleapproach.jqueryboostraptable.repository.UserRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;


    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String personalId) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByPersonalId(personalId);
            if (user == null) {
                throw new UsernameNotFoundException(personalId);
            }
            System.out.println("************loadUserByUsername : start " + user.getPersonalId());
            return new org.springframework.security.core.userdetails.User(user.getPersonalId(), user.getPass(),
                    user.getAuthorities());
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException(personalId);
        }


    }
}