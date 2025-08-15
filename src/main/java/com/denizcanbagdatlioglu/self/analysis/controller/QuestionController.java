package com.denizcanbagdatlioglu.self.analysis.controller;

import com.denizcanbagdatlioglu.self.analysis.application.AnalysisService;
import com.denizcanbagdatlioglu.self.analysis.controller.dto.QuestionResponse;
import com.denizcanbagdatlioglu.self.analysis.domain.entity.Question;
import com.denizcanbagdatlioglu.self.config.jwt.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
public class QuestionController {

    private final AnalysisService service;

    @GetMapping
    public ResponseEntity<QuestionResponse> getQuestion(JwtAuthentication authentication) {
        Optional<Question> maybeQuestion = service.generateQuestion(authentication.getName());

        return maybeQuestion
                .map(Question::get)
                .map(QuestionResponse::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.internalServerError().build());
    }

}
