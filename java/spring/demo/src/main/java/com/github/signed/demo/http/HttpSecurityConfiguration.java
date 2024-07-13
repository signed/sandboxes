package com.github.signed.demo.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

// https://www.baeldung.com/spring-security-method-security
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        securedEnabled = true
)
public class HttpSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/security/admin").authenticated()
                        .anyRequest().permitAll() //TODO permit all by default is just to get it working for now, should be deny by default and allow list the apis that should be public
                ).httpBasic(Customizer.withDefaults())
                //.csrf(AbstractHttpConfigurer::disable) // WARNING: Only turn this of if you do NOT use cookies
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails annabelleAdmin =
                User.withDefaultPasswordEncoder()
                        .username("annabelle")
                        .password("annabelle")
                        .roles("ADMIN", "USER")
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
