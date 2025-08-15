package com.denizcanbagdatlioglu.self.analysis.repository;

import com.denizcanbagdatlioglu.self.analysis.domain.entity.Analysis;
import com.denizcanbagdatlioglu.self.analysis.domain.repository.IAnalysisRepository;
import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@TestConfiguration
public class AnalysisRepositoryIntegrationTestConfiguration {

    @Bean
    public IAnalysisRepository analysisRepository(JdbcTemplate jdbcTemplate) {
        return new AnalysisRepository(jdbcTemplate);
    }

    @Bean
    public Analysis analysis() {
        return Analysis.builder()
                .id(ID.of("d5a4f8e2-7c31-4b9d-ae56-1f9c2b803e5d"))
                .insight("Insight Content")
                .analysis("Analysis Content").build();
    }

}
