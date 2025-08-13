package com.denizcanbagdatlioglu.self.common.domain.valueobject;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;



public class BirthDate {

    private final LocalDate value;

    public BirthDate(LocalDate value) {
        this.value = value;
    }

    public int getAge() {
        return Period.between(value, LocalDate.now()).getYears();
    }

    public LocalDate getDate() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof BirthDate otherDate)) return false;
        return this.value.equals(otherDate.value);
    }

    @Override
    public int hashCode() {
        return value != null ? Objects.hash(value) : 0;
    }

}