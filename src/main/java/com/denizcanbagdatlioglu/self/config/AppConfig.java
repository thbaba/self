package com.denizcanbagdatlioglu.self.config;

import com.denizcanbagdatlioglu.self.registration.application.UserRegistrationService;
import com.denizcanbagdatlioglu.self.user.domain.repository.IUserRepository;
import com.denizcanbagdatlioglu.self.wiki.application.FigureService;
import com.denizcanbagdatlioglu.self.wiki.domain.entity.Archetype;
import com.denizcanbagdatlioglu.self.wiki.domain.entity.Symbol;
import com.denizcanbagdatlioglu.self.wiki.domain.valueobject.FigureType;
import com.denizcanbagdatlioglu.self.wiki.repository.FigureRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class AppConfig {

    @Bean
    public UserRegistrationService userRegistrationService(IUserRepository userRepository) {
        return new UserRegistrationService(userRepository);
    }

    @Bean
    public FigureService<Archetype> archetypeService(FigureRepository<Archetype> archetypeRepository) {
        return new FigureService<Archetype>(archetypeRepository);
    }

    @Bean
    public FigureService<Symbol> symbolService(FigureRepository<Symbol> symbolRepository) {
        return new FigureService<Symbol>(symbolRepository);
    }

    @Bean
    public FigureRepository<Archetype> archetypeRepository(JdbcTemplate jdbcTemplate) {
        return new FigureRepository<>(jdbcTemplate, FigureType.ARCHETYPE, Archetype.builder());
    }

    @Bean
    public FigureRepository<Symbol> symbolRepository(JdbcTemplate jdbcTemplate) {
        return new FigureRepository<>(jdbcTemplate, FigureType.SYMBOL, Symbol.builder());
    }

}
