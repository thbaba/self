package com.denizcanbagdatlioglu.self.registration.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRegistrationResponse(
    @NotBlank String jwtToken
) {

}
