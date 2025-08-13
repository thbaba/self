package com.denizcanbagdatlioglu.self.user.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRegistrationResponse(
    @NotBlank String jwtToken
) {

}
