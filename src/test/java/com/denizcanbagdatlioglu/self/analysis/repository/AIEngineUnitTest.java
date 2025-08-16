package com.denizcanbagdatlioglu.self.analysis.repository;

import com.denizcanbagdatlioglu.self.analysis.domain.entity.Analysis;
import com.denizcanbagdatlioglu.self.analysis.repository.exception.AIAgentException;
import com.denizcanbagdatlioglu.self.common.domain.valueobject.BirthDate;
import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;
import org.wiremock.spring.InjectWireMock;

import java.time.LocalDate;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
classes = AIEngineUnitTest.AppConfig.class)
@EnableWireMock({
        @ConfigureWireMock(name = "ai-server", port = 0)
})
@TestPropertySource(properties = {
        "self.ai.model=gemma3:4b",
        "self.ai.timeout=1",
        "self.ai.history-size=20",
        "self.ai.url=${wiremock.server.baseUrl}/api/chat",
        "spring.flyway.enabled=false"
})
public class AIEngineUnitTest {
    @InjectWireMock("ai-server")
    WireMockServer server;

    @Autowired
    private AIEngine engine;

    @Test
    public void analyzeSuccessfullyTest(
            @Autowired BirthDate birthDate,
            @Autowired List<Analysis> analyses,
            @Autowired String insight,
            @Autowired MappingBuilder mappingBuilder
    ) {
        server.stubFor(mappingBuilder
                .withRequestBody(matchingJsonPath("$.messages[5].content", containing("Insight Text")))
                .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody("{\n" +
                                "\t\"message\": {\n" +
                                "        \"role\": \"assistant\",\n" +
                                "        \"content\": \"New analysis!\\n\"\n" +
                                "    }\n" +
                                "}"))
        );

        String response = engine.analyze(birthDate, insight, analyses);

        Assertions.assertThat(response).isEqualTo("New analysis!");
    }

    @Test
    public void questionSuccessfullyTest(
            @Autowired BirthDate birthDate,
            @Autowired List<Analysis> analyses,
            @Autowired MappingBuilder mappingBuilder
    ) {
        server.stubFor(mappingBuilder
                .withRequestBody(matchingJsonPath("$.messages[5].content"))
                .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody("{\n" +
                                "\t\"message\": {\n" +
                                "        \"role\": \"assistant\",\n" +
                                "        \"content\": \"New question!\\n\"\n" +
                                "    }\n" +
                                "}"))
        );

        String response = engine.get(birthDate, analyses);

        Assertions.assertThat(response).isEqualTo("New question!");
    }

    @Test
    public void shouldAnalyzeThrowOnServerError(
            @Autowired BirthDate birthDate,
            @Autowired List<Analysis> analyses,
            @Autowired String insight
    ) {
        server.stubFor(post("/api/chat").willReturn(aResponse().withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())));

        Assertions.assertThatThrownBy(() ->
                engine.analyze(birthDate, insight, analyses)
        ).isInstanceOf(AIAgentException.class);

    }

    @Test
    public void shouldAnalyzeThrowOnServerNullMessage(
            @Autowired BirthDate birthDate,
            @Autowired List<Analysis> analyses,
            @Autowired String insight
    ) {
        server.stubFor(post("/api/chat").willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json; chatset=utf-8")));

        Assertions.assertThatThrownBy(
                () -> engine.analyze(birthDate, insight, analyses)
        ).isInstanceOf(AIAgentException.class);
    }

    @Test
    public void shouldQuestionThrowOnServerError(
            @Autowired BirthDate birthDate,
            @Autowired List<Analysis> analyses
    ) {
        server.stubFor(post("/api/chat").willReturn(aResponse().withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())));

        Assertions.assertThatThrownBy(() -> engine.get(birthDate, analyses)).isInstanceOf(AIAgentException.class);
    }

    @Test
    public void shouldQuestionThrowOnServerNullMessage(
            @Autowired BirthDate birthDate,
            @Autowired List<Analysis> analyses
    ) {
        server.stubFor(post("/api/chat").willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json; chatset=utf-8")));

        Assertions.assertThatThrownBy(() -> engine.get(birthDate, analyses)).isInstanceOf(AIAgentException.class);
    }

    @SpringBootApplication
    static class AppConfig {

        @Bean
        public List<Analysis> analyses() {
            return List.of(
                    Analysis.builder().id(ID.random()).insight("Insight 1").analysis("Analysis 1").build(),
                    Analysis.builder().id(ID.random()).insight("Insight 2").analysis("Analysis 2").build()
            );
        }

        @Bean
        public String insight() {
            return "Insight Text";
        }

        @Bean
        public BirthDate birthDate() {
            return new BirthDate(LocalDate.now().minusYears(18));
        }

        @Bean
        public MappingBuilder mappingBuilder() {
            return post("/api/chat")
                    .withRequestBody(matchingJsonPath("$.model"))
                    .withRequestBody(matchingJsonPath("$.messages[0].role", equalTo("system")))
                    .withRequestBody(matchingJsonPath("$.messages[0].content", containing("Danışanın 18 yaşında.")))
                    .withRequestBody(matchingJsonPath("$.messages[1].role", equalTo("user")))
                    .withRequestBody(matchingJsonPath("$.messages[1].content", equalTo("Insight 1")))
                    .withRequestBody(matchingJsonPath("$.messages[2].role", equalTo("assistant")))
                    .withRequestBody(matchingJsonPath("$.messages[2].content", equalTo("Analysis 1")))
                    .withRequestBody(matchingJsonPath("$.messages[3].role", equalTo("user")))
                    .withRequestBody(matchingJsonPath("$.messages[3].content", equalTo("Insight 2")))
                    .withRequestBody(matchingJsonPath("$.messages[4].role", equalTo("assistant")))
                    .withRequestBody(matchingJsonPath("$.messages[4].content", equalTo("Analysis 2")))
                    .withRequestBody(matchingJsonPath("$.messages[5].role", equalTo("user")));
        }

        @Bean
        public AIEngine engine(@Value("${self.ai.model}") String model) {
            return new AIEngine(new RestTemplate(), model);
        }
    }
}
