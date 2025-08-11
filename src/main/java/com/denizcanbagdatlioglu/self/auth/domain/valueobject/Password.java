package com.denizcanbagdatlioglu.self.auth.domain.valueobject;


public class Password {

    private final String value;

    private Password(String value) {
        this.value = value;
    }

    public static Password of(String value) {
        if(!isEncodedWithBcrypt(value)) {
            throw new IllegalArgumentException("Password is not hashed.");
        }
        return new Password(value);
    }

    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Password other))
            return false;
        return value.equals(other.value);
    }

    public String getValue() {
        return value;
    }

    private static boolean isEncodedWithBcrypt(String value) {
        return value != null && value.matches("^\\{bcrypt}\\$2[aby]\\$\\d{2}\\$[./A-Za-z0-9]+$");
    }
}
