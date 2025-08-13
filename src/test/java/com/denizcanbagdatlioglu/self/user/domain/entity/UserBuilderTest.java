package com.denizcanbagdatlioglu.self.user.domain.entity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import com.denizcanbagdatlioglu.self.user.domain.valueobject.Gender;
import com.denizcanbagdatlioglu.self.common.domain.valueobject.BirthDate;

@DisplayName("UserBuilder test.")
public class UserBuilderTest {

    
    private String id;

    @BeforeEach
    public void setUp() {
        id = UUID.randomUUID().toString();
    }

    @Test
    @DisplayName("User builder test.")
    public void builderTest() {

        ID userID = ID.of(id);
        BirthDate userBirthDate= new BirthDate(LocalDate.now().minusYears(18));
        Gender userGender = Gender.FEMALE;

        User user = User.builder().id(userID)
            .birthDate(userBirthDate)
            .gender(userGender)
            .build();

        assertThat(user).satisfies(u -> { 
            assertThat(u.id()).isEqualTo(userID);
            assertThat(u.birthDate()).isEqualTo(userBirthDate);
            assertThat(u.gender()).isEqualTo(userGender);
        });
    }

}
