package com.denizcanbagdatlioglu.self.wiki.repository;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import com.denizcanbagdatlioglu.self.wiki.domain.entity.Archetype;
import com.denizcanbagdatlioglu.self.wiki.domain.entity.Figure;
import com.denizcanbagdatlioglu.self.wiki.domain.entity.Symbol;
import com.denizcanbagdatlioglu.self.wiki.domain.valueobject.FigureType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@JdbcTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Figure repository integration test")
public class FigureRepositoryTest {

    @Container
    private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpassword");

    @DynamicPropertySource
    public static void configurePropertySource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.flyway.enabled", () -> false);
    }

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private FigureRepository<Archetype> archetypeRepository;

    private FigureRepository<Symbol> symbolRepository;

    private List<String> archetypeNames;

    private List<String> symbolNames;

    private Archetype archetype;

    private Symbol symbol;

    @BeforeEach
    public void setUp() {
        archetypeRepository = new FigureRepository<Archetype>(jdbcTemplate, FigureType.ARCHETYPE, Archetype.builder());
        symbolRepository = new FigureRepository<Symbol>(jdbcTemplate, FigureType.SYMBOL, Symbol.builder());
        ResourceDatabasePopulator dbPopulator = new ResourceDatabasePopulator(
                new ClassPathResource("db/FigureRepositoryTest.sql")
        );
        dbPopulator.setContinueOnError(false);
        dbPopulator.execute(dataSource);

        archetypeNames = List.of("Archetype1", "Archetype2", "Archetype3", "Archetype4");
        symbolNames = List.of("Symbol1", "Symbol2", "Symbol3", "Symbol4");
        archetype = Archetype.builder().id(ID.random()).name("AAA").brief("AAA").description("AAA").build();
        symbol = Symbol.builder().id(ID.random()).name("SSS").brief("SSS").description("SSS").build();
    }

    @Test
    @DisplayName("Get all archetypes with greater pageSize than entry count")
    public void getAllArchetypes() {
        List<Archetype> archetypes = archetypeRepository.getFiguresWithPagination(100, 0);

        Assertions.assertThat(archetypes).hasSize(4)
                .map(Archetype::name)
                .containsExactlyInAnyOrderElementsOf(archetypeNames);
    }

    @Test
    @DisplayName("Get all archetypes with greater pageSize than entry count")
    public void getAllSymbols() {
        List<Symbol> symbols = symbolRepository.getFiguresWithPagination(100, 0);

        Assertions.assertThat(symbols).hasSize(4)
                .map(Symbol::name)
                .containsExactlyInAnyOrderElementsOf(symbolNames);
    }

    @Test
    @DisplayName("Get archetypes with pagination")
    public void getArchetypesWithPagination() {
        List<Archetype> archetypes = archetypeRepository.getFiguresWithPagination(2, 1);

        Assertions.assertThat(archetypes).hasSize(2)
                .map(Figure::name)
                .allMatch(archetypeNames::contains);
    }

    @Test
    @DisplayName("Get symbols with pagination")
    public void getSymbolsWithPagination() {
        List<Symbol> symbols = symbolRepository.getFiguresWithPagination(2, 1);

        Assertions.assertThat(symbols).hasSize(2)
                .map(Figure::name).allMatch(symbolNames::contains);
    }

    @Test
    @DisplayName("Get specific existing archetype by id")
    public void getArchetypeByID() {
        String sql = "insert into figures (id, name, brief, description, figure) values (?, ?, ?, ?, ?::figuretype)";
        ID id = archetype.id();
        jdbcTemplate.update(sql, id.asUuid(), archetype.name(),
                archetype.brief(), archetype.description(), FigureType.ARCHETYPE.name());

        Optional<Archetype> found = archetypeRepository.getFigureByID(id);

        Assertions.assertThat(found).isPresent().hasValue(archetype);
    }

    @Test
    @DisplayName("Get empty optional when try get non existing archetype")
    public void getNonExistingArchetype() {
        var notFound = archetypeRepository.getFigureByID(ID.random());

        Assertions.assertThat(notFound).isEmpty();
    }

    @Test
    @DisplayName("Get specific existing symbol by id")
    public void getSymbolByID() {
        String sql = "insert into figures (id, name, brief, description, figure) values (?, ?, ?, ?, ?::figuretype)";
        ID id = symbol.id();
        jdbcTemplate.update(sql, id.asUuid(), symbol.name(),
                symbol.brief(), symbol.description(), FigureType.SYMBOL.name());

        var found = symbolRepository.getFigureByID(id);

        Assertions.assertThat(found).isPresent().hasValue(symbol);
    }

    @Test
    @DisplayName("Get empty optional when try get non existing symbol")
    public void getNonExistingSymbol() {
        var notFound = symbolRepository.getFigureByID(ID.random());

        Assertions.assertThat(notFound).isEmpty();
    }
}
