package com.denizcanbagdatlioglu.self.user.domain.entity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import com.denizcanbagdatlioglu.self.common.domain.valueobject.BirthDate;
import com.denizcanbagdatlioglu.self.user.domain.valueobject.Gender;

@ExtendWith(MockitoExtension.class)
@DisplayName("User entity test.")
public class UserTest {

    @Mock
    BirthDate birthDate;

    private User user;

    @BeforeEach
    public void setUp() {
        user = User.builder().id(ID.random()).birthDate(birthDate).gender(Gender.MALE).build();
    }

    @Test
    @DisplayName("User is adult test.")
    public void userIsAdultTest() {

        when(birthDate.getAge()).thenReturn(18);

        boolean isAdult = user.isAdult();

        assertThat(isAdult).isTrue();
        verify(birthDate).getAge();
    }

    @Test
    @DisplayName("User is not adult test.")
    public void userIsNotAdultTest() {
        when(birthDate.getAge()).thenReturn(17);

        boolean isAdult = user.isAdult();

        assertThat(isAdult).isFalse();
        verify(birthDate).getAge();
    }

}
