package com.denizcanbagdatlioglu.self.registration.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.denizcanbagdatlioglu.self.registration.dto.UserRegistrationResponse;
import com.denizcanbagdatlioglu.self.registration.exception.UserRegistrationException;
import com.denizcanbagdatlioglu.self.registration.mapper.RegistrationMapper;
import com.denizcanbagdatlioglu.self.user.domain.entity.User;

import lombok.RequiredArgsConstructor;

import com.denizcanbagdatlioglu.self.config.jwt.JwtUtil;
import com.denizcanbagdatlioglu.self.registration.domain.usecase.IRegistrationUseCase;
import com.denizcanbagdatlioglu.self.registration.dto.UserRegistrationRequest;

import java.util.Optional;

@RestController
@RequestMapping("/api/registration")
@RequiredArgsConstructor
public class UserRegistrationController {

    private final RegistrationMapper registrationMapper;

    private final IRegistrationUseCase userRegistrationService;

    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UserRegistrationResponse> userRegistration(@RequestBody UserRegistrationRequest request) {
        User requestedUser = registrationMapper.toUser(request);
        Optional<User> maybeUser = userRegistrationService.register(requestedUser);

        return maybeUser.map(jwtUtil::generateToken)
                .map(UserRegistrationResponse::new)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UserRegistrationException("Registration failed!"));
    }

}
