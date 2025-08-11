package com.denizcanbagdatlioglu.self.auth.domain.service;

import com.denizcanbagdatlioglu.self.auth.domain.exception.InvalidPasswordException;
import com.denizcanbagdatlioglu.self.auth.domain.exception.MismatchedConfirmationPasswordException;
import com.denizcanbagdatlioglu.self.auth.domain.valueobject.Password;
import com.denizcanbagdatlioglu.self.auth.domain.valueobject.RawPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationPasswordConfirmationServiceTest {

    @Mock
    private RawPassword rawPassword;

    private Password password;

    @BeforeEach
    public void setUp() {
        password = Password.of("{bcrypt}$2a$10$Qf1j4YxG14yXDnAGfv7wru0TlUmc5zWMBMzi0jAaz1eucyDDRiCh2");
    }

    @Test
    public void confirmPasswordTest() throws InvalidPasswordException, MismatchedConfirmationPasswordException {
        doNothing().when(rawPassword).confirm();
        RegistrationPasswordConfirmationService service = new RegistrationPasswordConfirmationService();
        service.confirmPassword(rawPassword, password, password);
        verify(rawPassword, times(1)).confirm();
    }

}
