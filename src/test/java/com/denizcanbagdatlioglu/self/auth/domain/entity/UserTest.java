package com.denizcanbagdatlioglu.self.auth.domain.entity;

import com.denizcanbagdatlioglu.self.auth.domain.valueobject.BirthDate;
import com.denizcanbagdatlioglu.self.auth.domain.valueobject.Gender;
import com.denizcanbagdatlioglu.self.auth.domain.valueobject.Password;
import com.denizcanbagdatlioglu.self.auth.domain.valueobject.Username;
import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    @Mock
    private BirthDate birthDate;

    @Mock
    private ID id;

    @Mock
    private Username username;

    @Mock
    private Password password;

    @Mock
    private Gender gender;

    @InjectMocks
    private User user;

    @Test
    public void userIsAdult() {
        when(birthDate.getAge()).thenReturn(18);

        boolean isAdult = user.isAdult();

        assertThat(isAdult).isTrue();
        verify(birthDate, times(1)).getAge();
    }

    @Test
    public void userIsNotAdult() {
        when(birthDate.getAge()).thenReturn(17);

        boolean isAdult = user.isAdult();

        assertThat(isAdult).isFalse();
        verify(birthDate, times(1)).getAge();
    }

}
