package com.denizcanbagdatlioglu.self.registration.application;

import java.util.Optional;

import com.denizcanbagdatlioglu.self.registration.domain.usecase.IRegistrationUseCase;
import com.denizcanbagdatlioglu.self.user.domain.entity.User;
import com.denizcanbagdatlioglu.self.user.domain.repository.IUserRepository;

public class UserRegistrationService implements IRegistrationUseCase{

    private final IUserRepository userRepository;

    public UserRegistrationService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> register(User user) {
        if(!user.isAdult())
            return Optional.empty();

        return userRepository.registerUser(user);
    }



}
