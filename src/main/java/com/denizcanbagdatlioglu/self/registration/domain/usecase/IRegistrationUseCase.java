package com.denizcanbagdatlioglu.self.registration.domain.usecase;

import java.util.Optional;

import com.denizcanbagdatlioglu.self.user.domain.entity.User;

public interface IRegistrationUseCase {

    Optional<User> register(User user);

}
