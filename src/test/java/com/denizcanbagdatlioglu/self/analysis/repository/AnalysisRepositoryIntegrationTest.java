package com.denizcanbagdatlioglu.self.analysis.repository;

import com.denizcanbagdatlioglu.self.analysis.domain.entity.Analysis;
import com.denizcanbagdatlioglu.self.analysis.domain.repository.IAnalysisRepository;
import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@JdbcTest
@Testcontainers
@Import(AnalysisRepositoryIntegrationTestConfiguration.class)
public class AnalysisRepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17")
            .withDatabaseName("testdb")
            .withUsername("testusername")
            .withPassword("testpassword");
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private DataSource dataSource;

    private ID existingUserID;
    private ID nonExistingUserID;
    private ID existingInsightID;
    private ID nonExistingInsightID;

    @Autowired
    private IAnalysisRepository repository;

    @BeforeEach
    public void setUp() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("db/AnalysisRepositoryTest.sql"));
        populator.execute(dataSource);

        existingUserID = ID.of("a2f9b586-4d3e-439d-9626-043d3f7165c9");
        nonExistingUserID = ID.of("9811ffeb-a950-4609-862d-2f898a05931f");
        existingInsightID = ID.of("c0a3812d-2f7e-4043-bc78-4dfa53d08dff");
        nonExistingInsightID = ID.of("fe6bd59e-6598-4d45-9646-6089304fff8a");
    }

    @Test
    public void shouldFindInsightByIDWithCorrectUserIDAndInsightID() {
        String expectedInsight = "Insight Content";

        Optional<String> maybeInsight = repository.findInsightByID(existingUserID, existingInsightID);

        Assertions.assertThat(maybeInsight).isPresent()
                .contains(expectedInsight);

    }

    @Test
    public void shouldCanNotFindInsightByIDWithCorrectUserIDAndNonExistingInsightID() {
        Optional<String> maybeInsight = repository.findInsightByID(existingUserID, nonExistingInsightID);

        Assertions.assertThat(maybeInsight).isEmpty();
    }

    @Test
    public void shouldCanNotFindInsightByIDWithWrongUserIDAndExistingInsightID() {
        Optional<String> maybeInsight = repository.findInsightByID(nonExistingUserID, existingInsightID);

        Assertions.assertThat(maybeInsight).isEmpty();
    }

    @Test
    public void shouldFindBirthDateWithExistingUserID() {
        LocalDate expectedBirthDate = LocalDate.of(1990, 1, 1);

        Optional<LocalDate> maybeBirthDate = repository.findBirthDateByID(existingUserID);

        Assertions.assertThat(maybeBirthDate).isPresent()
                .contains(expectedBirthDate);
    }

    @Test
    public void shouldNotFindBirthDateWithNonExistingUserID() {
        Optional<LocalDate> maybeBirthDate = repository.findBirthDateByID(nonExistingUserID);

        Assertions.assertThat(maybeBirthDate).isEmpty();
    }


    @Test
    public void shouldFindExistingAnalysisWithCorrectUserIDAndCorrectInsightID(@Autowired Analysis expected) {
        Optional<Analysis> maybeAnalysis = repository.findAnalysisByInsightID(existingUserID, existingInsightID);

        Assertions.assertThat(maybeAnalysis).isPresent()
                .contains(expected);
    }

    @Test
    public void shouldNotFindExistingAnalysisWithCorrectUserIDAndWrongInsightID() {
        var result = repository.findAnalysisByInsightID(existingUserID, nonExistingInsightID);

        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void shouldNotFindExistingAnalysisWithNonExistingUserIDAndCorrectInsightID() {
        var result = repository.findAnalysisByInsightID(nonExistingUserID, existingInsightID);

        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void shouldFindAnalysesWithCorrectUserID(@Autowired Analysis expected) {
        List<Analysis> analyses = repository.findAnalyses(existingUserID, Integer.MAX_VALUE);

        Assertions.assertThat(analyses).hasSize(1)
                .containsExactly(expected);
    }

    @Test
    public void shouldNotFindAnalysesWithCorrectUserIDAndZeroCount() {
        List<Analysis> analyses = repository.findAnalyses(existingUserID, 0);

        Assertions.assertThat(analyses).isEmpty();
    }

    @Test
    public void shouldNotFindAnalysesWithNonExistingUserID() {
        List<Analysis> analyses = repository.findAnalyses(nonExistingUserID, Integer.MAX_VALUE);

        Assertions.assertThat(analyses).isEmpty();
    }

    @Test
    public void shouldSaveAnalysisSuccessfully(@Autowired Analysis analysis) {
        cleanAnalysesTable();

        boolean result = repository.saveAnalysis(existingUserID, existingInsightID, analysis.analysis());

        Analysis read = getFromAnalysisFromAnalyses(existingInsightID);

        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(read).extracting(Analysis::insight).isEqualTo("Insight Content");
        Assertions.assertThat(read).extracting(Analysis::analysis).isEqualTo("Analysis Content");
    }

    @Test
    public void shouldNotSaveExistingAnalysis(@Autowired Analysis analysis) {
        boolean result = repository.saveAnalysis(existingUserID, existingInsightID, analysis.analysis());

        Assertions.assertThat(result).isFalse();
    }

    private void cleanAnalysesTable() {
        jdbcTemplate.execute("delete from analyses");
    };

    private Analysis getFromAnalysisFromAnalyses(ID insightID) {
        String sql = "select " +
                "analyses.id id, insights.content insight, analyses.analysis analysis " +
                "from analyses " +
                "inner join insights on analyses.insight_id = insights.id " +
                "inner join users on insights.author_id = users.id " +
                "where analyses.insight_id = ?";

        RowMapper<Analysis> rowMapper = (rs, _) ->
                Analysis.builder()
                        .id(ID.of(rs.getString("id")))
                        .insight(rs.getString("insight"))
                        .analysis(rs.getString("analysis"))
                        .build();

        return jdbcTemplate.queryForObject(sql, rowMapper, insightID.asUuid());
    }


}
