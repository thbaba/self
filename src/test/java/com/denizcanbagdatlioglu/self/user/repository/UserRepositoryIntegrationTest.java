package com.denizcanbagdatlioglu.self.user.repository;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import com.denizcanbagdatlioglu.self.user.domain.entity.User;
import com.denizcanbagdatlioglu.self.common.domain.valueobject.BirthDate;
import com.denizcanbagdatlioglu.self.user.domain.valueobject.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("User resository integration test.")
public class UserRepositoryIntegrationTest {

    @Container
    private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17")
        .withDatabaseName("testdb")
        .withUsername("testUser")
        .withPassword("testPassword")
        .withReuse(true);

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder().id(ID.random()).birthDate(new BirthDate(LocalDate.now())).gender(Gender.MALE).build();
        userRepository = new UserRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("findUserById successfully find user when user exists")
    public void findUserByIDShouldFindUserWhenExists() {
        jdbcTemplate.update("insert into users(id, birth_date, gender) values (?, ?, ?::gender)",
            user.id().asUuid(), 
            Date.valueOf(user.birthDate().getDate()),
            user.gender().name());

        Optional<User> maybeUser = userRepository.findUserByID(user.id());

        assertThat(maybeUser).isPresent()
            .contains(user);

        jdbcTemplate.update("delete from users");
    }

    @Test
    @DisplayName("findUserById can not find when user does not exist")
    public void findUserByIDshouldNotFindUserWhenDoesNotExist() {
        Optional<User> maybeUser = userRepository.findUserByID(user.id());

        assertThat(maybeUser).isEmpty();
    }

    @Test
    @DisplayName("registerUser should register user if there is no such user registered before")
    public void registerUserWhenThereIsNoSuchUserBefore() {
        jdbcTemplate.update("delete from users");

        Optional<User> maybeUser = userRepository.registerUser(user);

        assertThat(maybeUser).isPresent().contains(user);
    }

    @Test
    @DisplayName("Do not register user if there is already registered user with same id")
    public void registerUserFailWhenThereIsSuchAUser() {
        jdbcTemplate.update("insert into users(id, birth_date, gender) values (?, ?, ?::gender)",
            user.id().asUuid(), 
            Date.valueOf(user.birthDate().getDate()),
            user.gender().name());

        Optional<User> maybeUser = userRepository.registerUser(user);

        assertThat(maybeUser).isEmpty();

        jdbcTemplate.update("delete from users");
    }

}
