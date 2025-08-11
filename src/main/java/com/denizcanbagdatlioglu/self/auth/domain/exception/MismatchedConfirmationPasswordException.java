package com.denizcanbagdatlioglu.self.auth.domain.exception;

public class MismatchedConfirmationPasswordException extends Exception {
    public MismatchedConfirmationPasswordException(String message) {
        super(message);
    }
}
