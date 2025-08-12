package com.denizcanbagdatlioglu.self.config.jwt;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.security.auth.Subject;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class JwtAuthentication implements Authentication {

    private final String token;

    private final ID id;

    private boolean authenticated = false;

    @Setter
    private WebAuthenticationDetails details;

    public JwtAuthentication() {
        this.token = null;
        this.id = null;
        setAuthenticated(false);
    }

    public JwtAuthentication(String token) {
        this.token = token;
        this.id = null;
        setAuthenticated(false);
    }

    public JwtAuthentication(ID id)
    {
        this.token = null;
        this.id = id;
        setAuthenticated(true);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getCredentials() {
        return token;
    }

    @Override
    public WebAuthenticationDetails getDetails() {
        return details;
    }

    @Override
    public ID getPrincipal() {
        return id;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if(isAuthenticated && (id == null || id.getValue().isBlank()))
            throw new IllegalArgumentException("An ID must be supported for authentication.");
        this.authenticated = isAuthenticated;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof JwtAuthentication otherAuthentication)) return false;
        return id.equals(otherAuthentication.id) &&
                token.equals(otherAuthentication.token) &&
                authenticated == otherAuthentication.authenticated;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, authenticated);
    }

    @Override
    public String getName() {
        return id.getValue();
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }

}
