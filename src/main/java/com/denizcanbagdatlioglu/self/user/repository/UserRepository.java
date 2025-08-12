package com.denizcanbagdatlioglu.self.user.repository;

import java.sql.Date;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import com.denizcanbagdatlioglu.self.user.domain.entity.User;
import com.denizcanbagdatlioglu.self.user.domain.repository.IUserRepository;
import com.denizcanbagdatlioglu.self.user.domain.valueobject.BirthDate;
import com.denizcanbagdatlioglu.self.user.domain.valueobject.Gender;

@Repository
public class UserRepository implements IUserRepository{

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findUserByID(ID id) {
        Optional<User> maybeUser;
        try {
            User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE user_id = ?",
                (rs, _) -> {
                    ID userID = ID.of(rs.getString("user_id"));
                    BirthDate userBirthDate = new BirthDate(rs.getDate("birth_date").toLocalDate());
                    Gender userGender = Gender.valueOf(rs.getString("gender"));
                    return User.builder().id(userID).birthDate(userBirthDate).gender(userGender).build();
                },
                id.asUuid()
            );
            maybeUser = Optional.of(user);
        } catch(EmptyResultDataAccessException e) {
            maybeUser = Optional.empty();
        }

        return maybeUser;
    }

    @Override
    public Optional<User> registerUser(User user) {
        Optional<User> maybeUser = findUserByID(user.id());

        if(maybeUser.isPresent()) {
            return Optional.empty();
        }

        jdbcTemplate.update("insert into users(user_id, birth_date, gender) values (?, ?, ?::gender)", 
            user.id().asUuid(), 
            Date.valueOf(user.birthDate().getDate()),
            user.gender().name()
        );

        return findUserByID(user.id());
    }

}
