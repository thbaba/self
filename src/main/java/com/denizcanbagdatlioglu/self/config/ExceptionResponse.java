package com.denizcanbagdatlioglu.self.config;

public record ExceptionResponse(String error) {

    public static ExceptionResponse of(String error) {
        return new ExceptionResponse(error);
    }

}
