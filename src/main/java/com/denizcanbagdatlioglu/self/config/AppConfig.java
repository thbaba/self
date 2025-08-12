package com.denizcanbagdatlioglu.self.config;

import com.denizcanbagdatlioglu.self.registration.application.UserRegistrationService;
import com.denizcanbagdatlioglu.self.user.domain.repository.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public UserRegistrationService userRegistrationService(IUserRepository userRepository) {
        return new UserRegistrationService(userRepository);
    }

}
