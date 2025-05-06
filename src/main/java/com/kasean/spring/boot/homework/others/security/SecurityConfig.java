package com.kasean.spring.boot.homework.others.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v1/user/create").permitAll()
                        .requestMatchers("/v1/user/admin/**").hasRole("ADMIN")
                        .requestMatchers("/v1/user/**").authenticated()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll())
                .oauth2Login(httpSecurityOAuth2LoginConfigurer -> {})
                .headers(config -> config.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .logout(LogoutConfigurer::permitAll)
                .build();
    }
}
