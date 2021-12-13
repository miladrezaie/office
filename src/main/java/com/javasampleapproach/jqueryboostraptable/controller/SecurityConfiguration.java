package com.javasampleapproach.jqueryboostraptable.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;


//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
////        auth.userDetailsService(userDetailsService);
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                .antMatchers("/saveEmployee", "/findAllUser").permitAll()
                .antMatchers("/saat/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/registration").permitAll()
//                .antMatchers("/admin/roles").hasAnyAuthority("ADMIN")
//                .antMatchers("/times", "/user/**").hasAnyAuthority("ADMIN", "USER")
//                .antMatchers("/Esave", "/members", "/employee").hasAuthority("ADMIN")
                .anyRequest()
                .authenticated().and()
                .csrf().disable()
                .formLogin().loginPage("/login").failureUrl("/login?error=true")
                .defaultSuccessUrl("/panel")
                .usernameParameter("personalId")
                .passwordParameter("pass")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied");
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(
                        "/resources/**",
                        "/static/**",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/console/**",
                        "/admin/css/**",
                        "/admin/js/**",
                        "/admin/fonts/**",
                        "/admin/img/**"
                );
    }


}
