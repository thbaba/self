package com.denizcanbagdatlioglu.self.auth.domain.service;

import com.denizcanbagdatlioglu.self.auth.domain.exception.InvalidPasswordException;
import com.denizcanbagdatlioglu.self.auth.domain.exception.MismatchedConfirmationPasswordException;
import com.denizcanbagdatlioglu.self.auth.domain.valueobject.Password;
import com.denizcanbagdatlioglu.self.auth.domain.valueobject.RawPassword;

public class RegistrationPasswordConfirmationService {

    public void confirmPassword(RawPassword rawPassword, Password password, Password passwordConfirmation)
            throws InvalidPasswordException, MismatchedConfirmationPasswordException {
        rawPassword.confirm();
        if(!password.equals(passwordConfirmation)) {
            throw new MismatchedConfirmationPasswordException("Passwords do not match.");
        }
    }

}
