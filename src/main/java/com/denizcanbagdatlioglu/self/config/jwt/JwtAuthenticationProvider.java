package com.denizcanbagdatlioglu.self.config.jwt;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Authentication authenticate(Authentication authentication) {
        return Optional.ofNullable(authentication)
                .map(auth -> (JwtAuthentication) auth)
                .map(JwtAuthentication::getCredentials)
                .map(jwtUtil::getID)
                .map(ID::of)
                .map(JwtAuthentication::new)
                .orElse(new JwtAuthentication());
    }

    @Override
    public boolean supports(Class<?> authenticationClass) {
        return JwtAuthentication.class.isAssignableFrom(authenticationClass);
    }

}
