package com.denizcanbagdatlioglu.self.config;

import com.denizcanbagdatlioglu.self.analysis.application.AnalysisService;
import com.denizcanbagdatlioglu.self.analysis.domain.repository.IAnalysisRepository;
import com.denizcanbagdatlioglu.self.analysis.repository.AIEngine;
import com.denizcanbagdatlioglu.self.analysis.repository.AnalysisRepository;
import com.denizcanbagdatlioglu.self.config.jwt.JwtUserDetailsService;
import com.denizcanbagdatlioglu.self.insight.application.InsightService;
import com.denizcanbagdatlioglu.self.insight.domain.repository.IAuthorRepository;
import com.denizcanbagdatlioglu.self.insight.domain.repository.IInsightRepository;
import com.denizcanbagdatlioglu.self.insight.domain.service.InsightDateValidationService;
import com.denizcanbagdatlioglu.self.insight.repository.AuthorRepository;
import com.denizcanbagdatlioglu.self.insight.repository.InsightRepository;
import com.denizcanbagdatlioglu.self.user.application.UserRegistrationService;
import com.denizcanbagdatlioglu.self.user.domain.repository.IUserRepository;
import com.denizcanbagdatlioglu.self.wiki.application.FigureService;
import com.denizcanbagdatlioglu.self.wiki.domain.entity.Archetype;
import com.denizcanbagdatlioglu.self.wiki.domain.entity.Symbol;
import com.denizcanbagdatlioglu.self.wiki.domain.valueobject.FigureType;
import com.denizcanbagdatlioglu.self.wiki.repository.FigureRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

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

    @Bean
    public InsightService insightService(IAuthorRepository authorRepository, IInsightRepository insightRepository, InsightDateValidationService dateValidationService) {
        return new InsightService(insightRepository, authorRepository, dateValidationService);
    }

    @Bean
    public InsightDateValidationService insightDateValidationService() {
        return new InsightDateValidationService();
    }

    @Bean
    public IAuthorRepository authorRepository(JdbcTemplate jdbcTemplate) {
        return new AuthorRepository(jdbcTemplate);
    }

    @Bean
    public IInsightRepository insightRepository(JdbcTemplate jdbcTemplate) {
        return new InsightRepository(jdbcTemplate);
    }

    @Bean
    public IAnalysisRepository analysisRepository(JdbcTemplate jdbcTemplate) {
        return new AnalysisRepository(jdbcTemplate);
    }

    @Bean
    public AIEngine aiEngine(RestTemplate template,
                             @Value("${ai.model}") String model) {
        return new AIEngine(template, model);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder, @Value("${ai.timeout}") int minutes) {
        return builder.connectTimeout(Duration.ofSeconds(20))
                .readTimeout(Duration.ofMinutes(minutes))
                .build();
    }

    @Bean
    public AnalysisService analysisService(IAnalysisRepository repository, AIEngine aiEngine) {
        return new AnalysisService(repository, aiEngine, aiEngine);
    }

    @Bean
    public UserDetailsService userDetailsService(JdbcTemplate template) {
        return new JwtUserDetailsService(template);
    }

}
