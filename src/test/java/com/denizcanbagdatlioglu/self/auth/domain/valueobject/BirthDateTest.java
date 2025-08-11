package com.denizcanbagdatlioglu.self.auth.domain.valueobject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BirthDateTest {

    private BirthDate adult;
    private BirthDate notAdult;

    @BeforeEach
    public void setUp() {
        var adultLocalDate = LocalDate.now().minusYears(18);
        var notAdultLocalDate = LocalDate.now().minusYears(18).plusDays(1);

        adult = new BirthDate(adultLocalDate);
        notAdult = new BirthDate(notAdultLocalDate);
    }

    @Test
    @DisplayName("BirthDate get age test.")
    public void getAgeTest() {
        assertThat(adult).extracting(BirthDate::getAge).isEqualTo(18);
        assertThat(notAdult).extracting(BirthDate::getAge).isEqualTo(17);
    }

}
