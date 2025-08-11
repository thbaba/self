package com.denizcanbagdatlioglu.self.auth.domain.valueobject;

import com.denizcanbagdatlioglu.self.auth.domain.exception.InvalidPasswordException;
import com.denizcanbagdatlioglu.self.common.regex.RegexExecutor;


public record RawPassword(String value) {

    public void confirm() throws InvalidPasswordException {

        if(value == null)
            throw new InvalidPasswordException("Password cannot be null.");

        confirmLength();
        confirmNumeric();
        confirmLowerCase();
        confirmUpperCase();
        confirmSpecialCharacter();
        confirmNoWhitespace();
    }

    private void confirmLength() throws InvalidPasswordException{
        boolean c = value.length() >= 8;
        if(!c)
            throw new InvalidPasswordException("Password length must be at least 8 characters.");
    }

    private void confirmNumeric() throws InvalidPasswordException{
        boolean c = RegexExecutor.atLeastOne("[0-9]", value);
        if(!c)
            throw new InvalidPasswordException("Password must contain at least one numeric character.");
    }

    private void confirmLowerCase() throws InvalidPasswordException{
        boolean c = RegexExecutor.atLeastOne("[a-z]", value);
        if(!c)
            throw new InvalidPasswordException("Password must contain at least one lowercase character.");
    }

    private void confirmUpperCase() throws InvalidPasswordException{
        boolean c = RegexExecutor.atLeastOne("[A-Z]", value);
        if(!c)
            throw new InvalidPasswordException("Password must contain at least one uppercase character.");
    }

    private void confirmSpecialCharacter() throws InvalidPasswordException{
        boolean c = RegexExecutor.atLeastOne("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]", value);
        if(!c)
            throw new InvalidPasswordException("Password must contain at least one special character.");
    }

    private void confirmNoWhitespace() throws InvalidPasswordException{
        boolean c = RegexExecutor.noOne("\\s", value);
        if(!c)
            throw new InvalidPasswordException("Password can not contain whitespace.");
    }

}
