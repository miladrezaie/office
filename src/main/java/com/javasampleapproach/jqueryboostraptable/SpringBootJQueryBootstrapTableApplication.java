package com.javasampleapproach.jqueryboostraptable;


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


    public static void main(String[] args) {
        SpringApplication.run(SpringBootJQueryBootstrapTableApplication.class, args);

    }

}
