package com.denizcanbagdatlioglu.self.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;

@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String userID) throws UsernameNotFoundException {
        String sql = "SELECT * FROM users WHERE id = ?";
        RowMapper<UserDetails> mapper = (rs, _) -> JwtUserDetails.of(rs.getString("id"));

        try {
            UserDetails details = jdbcTemplate.queryForObject(sql, mapper, UUID.fromString(userID));
            return details;
        } catch (DataAccessException e) {
            throw new UsernameNotFoundException("No such user!");
        }
    }
}
