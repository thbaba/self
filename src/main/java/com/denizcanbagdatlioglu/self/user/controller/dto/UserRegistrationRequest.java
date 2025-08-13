package com.denizcanbagdatlioglu.self.user.controller.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


public record UserRegistrationRequest(
    @NotNull LocalDate birthDate, 
    @NotNull String gender) {

}
