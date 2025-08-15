package com.denizcanbagdatlioglu.self.analysis.repository;

import com.denizcanbagdatlioglu.self.analysis.domain.entity.Analysis;
import com.denizcanbagdatlioglu.self.analysis.domain.repository.IAnalyzeEngine;
import com.denizcanbagdatlioglu.self.analysis.domain.repository.IQuestionEngine;
import com.denizcanbagdatlioglu.self.analysis.repository.dto.AIMessage;
import com.denizcanbagdatlioglu.self.analysis.repository.dto.AIRequest;
import com.denizcanbagdatlioglu.self.analysis.repository.dto.AIResponse;
import com.denizcanbagdatlioglu.self.analysis.repository.exception.AIAgentException;
import com.denizcanbagdatlioglu.self.common.domain.valueobject.BirthDate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class AIEngine implements IAnalyzeEngine, IQuestionEngine {
    
    private final RestTemplate restTemplate;

    private final String model;

    @Value( "${self.ai.url}")
    private String aiUrl;

    @Override
    public String analyze(BirthDate birthDate, String insight, List<Analysis> analyses) {
        AIRequest request = request(birthDate, insight, analyses);
        return requestAndResponse(request);
    }

    @Override
    public String get(BirthDate birthDate, List<Analysis> analyses) {
        AIRequest request = request(birthDate, analyses);
        return requestAndResponse(request);
    }

    private String requestAndResponse(AIRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AIRequest> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<AIResponse> response =
                    restTemplate.postForEntity(aiUrl, entity, AIResponse.class);

            if(!response.getStatusCode().is2xxSuccessful())
                throw new AIAgentException("AI agent did not respond!");

            return response.getBody().message().content().trim().replaceAll("[*]+", "");
        } catch (NullPointerException | HttpServerErrorException e) {
            throw new AIAgentException("AI agent did not respond!");
        }
    }

    private AIRequest request(BirthDate birthDate, List<Analysis> analyses) {
        AIMessage systemMessage = systemMessage(birthDate);
        AIMessage userMessage = userMessage();
        List<AIMessage> conversation = conversationHistory(analyses);

        conversation.addFirst(systemMessage);
        conversation.addLast(userMessage);

        return new AIRequest(model, conversation, false);
    }

    private AIRequest request(BirthDate birthDate, String insight, List<Analysis> analyses) {
        AIMessage systemMessage = systemMessage(birthDate);
        AIMessage userMessage = userMessage(insight);
        List<AIMessage> conversation = conversationHistory(analyses);

        conversation.addFirst(systemMessage);
        conversation.addLast(userMessage);

        return new AIRequest(model, conversation, false);
    }

    private List<AIMessage> conversationHistory(List<Analysis> analyses) {
        return analyses.stream().flatMap(analysis -> Stream.of(
                new AIMessage("user", analysis.insight()),
                new AIMessage("assistant", analysis.analysis())
        )).collect(Collectors.toList());
    }

    private AIMessage systemMessage(BirthDate birthDate) {
        return new AIMessage("system", new StringBuilder()
                .append("Sen bir psikologsun. Jungien tarzda terapi yapıyorsun. Danışanın ")
                .append(birthDate.getAge())
                .append(" yaşında. Geçmiş konuşmalarınızı da göz önünde bulundurarak danışanının yazdıklarını jungien tarzda analiz ediyorsun. Kısa ama öz konuşmayı tercih ediyorsun. Türkçe konuşuyorsun.")
                .toString());
    }

    private AIMessage userMessage() {
        return new AIMessage("user", "Geçmiş konuşmalarımızı göz önünde bulundurarak üzerine düşüneceğim tetikleyici bir soru sorar mısınız?");
    }

    private AIMessage userMessage(String insight) {
        return new AIMessage("user", new StringBuilder()
                .append(insight)
                .append(" Yeni yazımı analiz eder misiniz?")
                .toString());
    }
}
