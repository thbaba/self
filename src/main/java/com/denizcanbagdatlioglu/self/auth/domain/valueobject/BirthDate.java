package com.denizcanbagdatlioglu.self.auth.domain.valueobject;

import java.time.LocalDate;
import java.time.Period;

public record BirthDate(LocalDate value) {

    public int getAge() {
        return Period.between(value, LocalDate.now()).getYears();
    }

}
