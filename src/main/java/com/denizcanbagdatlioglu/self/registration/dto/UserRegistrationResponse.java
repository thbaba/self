package com.denizcanbagdatlioglu.self.registration.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRegistrationResponse(
    @NotBlank String jwtToken
) {

}
