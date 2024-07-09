package com.github.signed.demo.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((requests) -> requests
                        .anyRequest().permitAll()
                ).build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails annabelleAdmin =
                User.withDefaultPasswordEncoder()
                        .username("annabelle")
                        .password("annabelle")
                        .roles("ADMIN")
                        .build();
        UserDetails ulfUser =
                User.withDefaultPasswordEncoder()
                        .username("ulf")
                        .password("ulf")
                        .roles("USER")
                        .build();


        return new InMemoryUserDetailsManager(annabelleAdmin, ulfUser);
    }

}
