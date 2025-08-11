package com.denizcanbagdatlioglu.self.auth.app;

import com.denizcanbagdatlioglu.self.auth.domain.entity.User;
import com.denizcanbagdatlioglu.self.auth.domain.exception.InvalidPasswordException;
import com.denizcanbagdatlioglu.self.auth.domain.exception.MismatchedConfirmationPasswordException;
import com.denizcanbagdatlioglu.self.auth.domain.repository.IUserRepository;
import com.denizcanbagdatlioglu.self.auth.domain.service.RegistrationPasswordConfirmationService;
import com.denizcanbagdatlioglu.self.auth.domain.valueobject.*;
import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private RegistrationPasswordConfirmationService passConfirmService;

    @InjectMocks
    private UserService userService;

    private Password password;

    private User testUser;

    @BeforeEach
    public void setUp() {
        var id = ID.random();
        var username = new Username("testUser");
        password = Password.of("{bcrypt}$2a$10$Qf1j4YxG14yXDnAGfv7wru0TlUmc5zWMBMzi0jAaz1eucyDDRiCh2");
        var gender = Gender.FEMALE;
        var birthDate = new BirthDate(LocalDate.now().minusYears(18));
        testUser = User.builder().id(id).username(username).password(password).gender(gender).birthDate(birthDate).build();
    }

    @Test
    public void shouldRegisterSuccessfully() throws MismatchedConfirmationPasswordException, InvalidPasswordException {
        when(userRepository.registerUser(any(User.class))).thenReturn(Optional.ofNullable(testUser));
        doNothing().when(passConfirmService).confirmPassword(any(), any(), any());

        Optional<User> registered = userService.register(testUser, new RawPassword("password"), password);

        assertThat(registered).isPresent()
                        .hasValueSatisfying(user -> assertThat(user).isEqualTo(testUser) );
        verify(userRepository, times(1)).registerUser(isA(User.class));
        verify(passConfirmService, times(1)).confirmPassword(any(), any(), any());
    }

}
