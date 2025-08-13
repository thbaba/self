package com.denizcanbagdatlioglu.self.common.domaín.valueobject;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.BirthDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("BirthDate value object test.")
public class BirthDateTest {

    @Test
    @DisplayName("getAge eighteen test.")
    public void getAgeEighteenTest() {
        LocalDate minusEighteen = LocalDate.now().minusYears(18);
        BirthDate birthDate = new BirthDate(minusEighteen);

        int age = birthDate.getAge();

        assertThat(age).isEqualTo(18);
    }

    @Test
    @DisplayName("getAge under eightteen test.")
    public void getAgeUnderEightTeenTest() {
        LocalDate minusEighteenPlusOneDay = LocalDate.now().minusYears(18).plusDays(1);
        BirthDate birthDate = new BirthDate(minusEighteenPlusOneDay);

        int age = birthDate.getAge();

        assertThat(age).isLessThan(18);
    }


}
