package com.javasampleapproach.jqueryboostraptable;


import com.javasampleapproach.jqueryboostraptable.controller.UserService;
import com.javasampleapproach.jqueryboostraptable.model.User;
import com.javasampleapproach.jqueryboostraptable.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class SpringBootJQueryBootstrapTableApplication {

//    @Autowired
//    private static BCryptPasswordEncoder bCryptPasswordEncoder;
//    @Autowired
//    private static UserService userService;

    public static void main(String[] args) {
//        User userNew =new User();
//        userNew.setFName("milad");
//        userNew.setLname("rezaie");
//        userNew.setPass(bCryptPasswordEncoder.encode("123456"));
//
//        userService.save(userNew);

        SpringApplication.run(SpringBootJQueryBootstrapTableApplication.class, args);
    }

}
