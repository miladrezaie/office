package com.javasampleapproach.jqueryboostraptable.controller;


public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}