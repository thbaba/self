package com.denizcanbagdatlioglu.self.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
        .csrf(c -> c.disable())
        .httpBasic(c -> c.disable())
        .formLogin(c -> c.disable())
        .logout(c -> c.disable())
        .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            request -> request.requestMatchers("/api/registration").permitAll()
        )
        .build();
    }

}
