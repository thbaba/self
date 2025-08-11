package com.denizcanbagdatlioglu.self.auth.app;

import com.denizcanbagdatlioglu.self.auth.domain.exception.InvalidPasswordException;
import com.denizcanbagdatlioglu.self.auth.domain.exception.MismatchedConfirmationPasswordException;
import com.denizcanbagdatlioglu.self.auth.domain.repository.IUserRepository;
import com.denizcanbagdatlioglu.self.auth.domain.service.RegistrationPasswordConfirmationService;
import com.denizcanbagdatlioglu.self.auth.domain.usecase.IUserRegistrationUseCase;
import com.denizcanbagdatlioglu.self.auth.domain.entity.User;
import com.denizcanbagdatlioglu.self.auth.domain.valueobject.Password;
import com.denizcanbagdatlioglu.self.auth.domain.valueobject.RawPassword;

import java.util.Optional;

public class UserService implements IUserRegistrationUseCase {

    private final IUserRepository userRepository;

    private final RegistrationPasswordConfirmationService registrationPasswordConfirmationService;

    public UserService(IUserRepository userRepository, RegistrationPasswordConfirmationService registrationPasswordConfirmationService) {
        this.userRepository = userRepository;
        this.registrationPasswordConfirmationService = registrationPasswordConfirmationService;
    }

    @Override
    public Optional<User> register(User user, RawPassword rawPassword, Password confirmationPassword)
            throws InvalidPasswordException, MismatchedConfirmationPasswordException {
        if(!user.isAdult()) {
            throw new IllegalArgumentException("User must be at least 18 years old.");
        }

        registrationPasswordConfirmationService.confirmPassword(rawPassword, user.password(), confirmationPassword);

        return userRepository.registerUser(user);
    }

}
