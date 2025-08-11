package com.denizcanbagdatlioglu.self.auth.domain.repository;

import com.denizcanbagdatlioglu.self.auth.domain.entity.User;

import java.util.Optional;

public interface IUserRepository {
    Optional<User> registerUser(User user);
}
