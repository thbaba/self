package com.denizcanbagdatlioglu.self.user.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import com.denizcanbagdatlioglu.self.user.domain.entity.User;
import com.denizcanbagdatlioglu.self.user.domain.repository.IUserRepository;
import com.denizcanbagdatlioglu.self.common.domain.valueobject.BirthDate;
import com.denizcanbagdatlioglu.self.user.domain.valueobject.Gender;

@ExtendWith(MockitoExtension.class)
@DisplayName("User Registration Service unit test.")
public class UserRegistrationServiceTest {

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserRegistrationService userRegistrationService;

    private User adultUser;

    private User youngUser;

    @BeforeEach
    void setUp() {
        BirthDate adultBirthDate = new BirthDate(LocalDate.now().minusYears(18));
        BirthDate youngBirthDate = new BirthDate(LocalDate.now().minusYears(17));

        adultUser = User.builder()
            .id(ID.random())
            .birthDate(adultBirthDate)
            .gender(Gender.MALE).build();

        youngUser = User.builder()
            .id(ID.random())
            .birthDate(youngBirthDate)
            .gender(Gender.FEMALE)
            .build();
    }

    @Test
    @DisplayName("registerUser when user is adult")
    public void shouldRegisterUserWhenUserAdult() {
        when(userRepository.registerUser(any(User.class))).thenReturn(Optional.of(adultUser));

        Optional<User> maybeUser = userRegistrationService.register(adultUser);

        assertThat(maybeUser).isPresent().contains(adultUser);
        verify(userRepository).registerUser(any(User.class));
    }

    @Test
    @DisplayName("Do not register user when user is not adult")
    public void shouldNotRegisterUserWhenUserIsNotAdult() {
        Optional<User> maybeUser = userRegistrationService.register(youngUser);

        assertThat(maybeUser).isEmpty();
        verify(userRepository, never()).registerUser(any(User.class));
    }

}
