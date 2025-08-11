package com.denizcanbagdatlioglu.self.common.domain.valueobject;

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

    public String getValue() {
        return value;
    }

}
