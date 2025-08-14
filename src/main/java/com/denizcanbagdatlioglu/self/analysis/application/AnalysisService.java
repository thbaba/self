package com.denizcanbagdatlioglu.self.analysis.application;

import com.denizcanbagdatlioglu.self.analysis.domain.entity.Analysis;
import com.denizcanbagdatlioglu.self.analysis.domain.entity.Question;
import com.denizcanbagdatlioglu.self.analysis.domain.repository.IAnalysisRepository;
import com.denizcanbagdatlioglu.self.analysis.domain.repository.IAnalyzeEngine;
import com.denizcanbagdatlioglu.self.analysis.domain.repository.IQuestionEngine;
import com.denizcanbagdatlioglu.self.analysis.domain.usecase.AnalyzeUseCase;
import com.denizcanbagdatlioglu.self.analysis.domain.usecase.GenerateQuestionUseCase;
import com.denizcanbagdatlioglu.self.analysis.domain.usecase.GetAnalysisUseCase;
import com.denizcanbagdatlioglu.self.common.domain.valueobject.BirthDate;
import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import com.denizcanbagdatlioglu.self.config.AppConst;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class AnalysisService implements AnalyzeUseCase, GenerateQuestionUseCase, GetAnalysisUseCase {

    private final IAnalysisRepository repository;

    private final IAnalyzeEngine analyzeEngine;

    private final IQuestionEngine questionEngine;


    public AnalysisService(IAnalysisRepository repository, IAnalyzeEngine analyzeEngine, IQuestionEngine questionEngine) {
        this.repository = repository;
        this.analyzeEngine = analyzeEngine;
        this.questionEngine = questionEngine;
    }


    @Override
    public Optional<Analysis> analyze(String userIDStr, String insightIDStr) {
        ID userID = ID.of(userIDStr);
        ID insightID = ID.of(insightIDStr);

        try {
            LocalDate birthDate = repository.findBirthDateByID(userID).get();
            String insight = repository.findInsightByID(userID, insightID).get();
            List<Analysis> analyses = repository.findAnalyses(userID, AppConst.AI_HISTORY_SIZE);

            String analysis = analyzeEngine.analyze(new BirthDate(birthDate), insight, analyses);

            return Optional.of(Analysis.builder().id(ID.random()).insight(insight).analysis(analysis).build());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Question> generateQuestion(String userIDStr) {
        ID userID = ID.of(userIDStr);

        try {
            LocalDate birthDate = repository.findBirthDateByID(userID).get();
            List<Analysis> analyses = repository.findAnalyses(userID, AppConst.AI_HISTORY_SIZE);

            String question = questionEngine.get(new BirthDate(birthDate), analyses);

            return Optional.of(Question.of(question));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Analysis> getAnalysis(String userID, String insightID) {
        return repository.findAnalysisByInsightID(ID.of(userID), ID.of(insightID));
    }
}
