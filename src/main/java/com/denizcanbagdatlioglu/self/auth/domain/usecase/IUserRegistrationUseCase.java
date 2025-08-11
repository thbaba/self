package com.denizcanbagdatlioglu.self.auth.domain.usecase;

import com.denizcanbagdatlioglu.self.auth.domain.entity.User;
import com.denizcanbagdatlioglu.self.auth.domain.exception.InvalidPasswordException;
import com.denizcanbagdatlioglu.self.auth.domain.exception.MismatchedConfirmationPasswordException;
import com.denizcanbagdatlioglu.self.auth.domain.valueobject.Password;
import com.denizcanbagdatlioglu.self.auth.domain.valueobject.RawPassword;

import java.util.Optional;


public interface IUserRegistrationUseCase {
    Optional<User> register(User user, RawPassword rawPassword, Password confirmationPassword)
            throws InvalidPasswordException, MismatchedConfirmationPasswordException;
}
