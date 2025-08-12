package com.denizcanbagdatlioglu.self;

import com.denizcanbagdatlioglu.self.registration.application.UserRegistrationService;
import com.denizcanbagdatlioglu.self.user.domain.repository.IUserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SelfApplication {

    public static void main(String[] args) {
        SpringApplication.run(SelfApplication.class, args);
    }

    @Bean
    public UserRegistrationService userRegistrationService(IUserRepository userRepository) {
        return new UserRegistrationService(userRepository);
    }

}
