package com.denizcanbagdatlioglu.self.analysis.controller;

import com.denizcanbagdatlioglu.self.analysis.application.AnalysisService;
import com.denizcanbagdatlioglu.self.analysis.controller.dto.AnalyzeResponse;
import com.denizcanbagdatlioglu.self.analysis.controller.dto.QuestionResponse;
import com.denizcanbagdatlioglu.self.analysis.domain.entity.Analysis;
import com.denizcanbagdatlioglu.self.analysis.domain.entity.Question;
import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import com.denizcanbagdatlioglu.self.config.jwt.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class AnalysisController {

    private final AnalysisService service;

    @PostMapping("/{insightID}")
    public ResponseEntity<AnalyzeResponse> analyze(
            @PathVariable(name = "insightID") String insightID,
            JwtAuthentication authentication
    ) {
        Optional<Analysis> maybeAnalysis = service.analyzeAndSave(authentication.getName(), insightID);

        return maybeAnalysis
                .map(analysis -> new AnalyzeResponse(analysis.id().getValue(), analysis.analysis()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok(new AnalyzeResponse(ID.random().getValue(), "Şu an bunun için zamanım yok!")));
    }

    @GetMapping("/{insightID}")
    public ResponseEntity<AnalyzeResponse> getAnalysis(@PathVariable String insightID, JwtAuthentication authentication) {
        Optional<Analysis> maybeAnalysis = service.getAnalysis(authentication.getName(), insightID);

        return maybeAnalysis
                .map(analysis -> new AnalyzeResponse(analysis.id().getValue(), analysis.analysis()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.internalServerError().build());
    }

    @GetMapping("/question")
    public ResponseEntity<QuestionResponse> getQuestion(JwtAuthentication authentication) {
        Optional<Question> maybeQuestion = service.generateQuestion(authentication.getName());

        return maybeQuestion
                .map(Question::get)
                .map(QuestionResponse::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.internalServerError().build());
    }

}
