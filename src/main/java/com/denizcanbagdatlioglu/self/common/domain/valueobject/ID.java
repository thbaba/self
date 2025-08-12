package com.denizcanbagdatlioglu.self.common.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

public class ID {

    private final String value;

    private ID(String value) {
        this.value = value;
    }

    public static ID random() {
        return new ID(UUID.randomUUID().toString());
    }

    public static ID of(String value) {
        return new ID(value);
    }

    public UUID asUuid() {
        return UUID.fromString(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof ID otherID)) return false;
        return this.value.equals(otherID.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
