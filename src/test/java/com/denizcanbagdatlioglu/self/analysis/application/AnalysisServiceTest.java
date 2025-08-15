package com.denizcanbagdatlioglu.self.analysis.application;

import com.denizcanbagdatlioglu.self.analysis.domain.entity.Analysis;
import com.denizcanbagdatlioglu.self.analysis.domain.entity.Question;
import com.denizcanbagdatlioglu.self.analysis.domain.repository.IAnalysisRepository;
import com.denizcanbagdatlioglu.self.analysis.domain.repository.IAnalyzeEngine;
import com.denizcanbagdatlioglu.self.analysis.domain.repository.IQuestionEngine;
import com.denizcanbagdatlioglu.self.common.domain.valueobject.BirthDate;
import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import com.denizcanbagdatlioglu.self.config.AppConst;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnalysisServiceTest {

    @Mock
    private IAnalysisRepository repository;

    @Mock
    private IAnalyzeEngine analyzeEngine;

    @Mock
    private IQuestionEngine questionEngine;

    @InjectMocks
    private AnalysisService service;

    @Test
    public void shouldAnalyze() {
        String userID = ID.random().getValue();
        String insightID = ID.random().getValue();
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        String insight = "Test insight";
        String analysisText = "Test analysis";

        when(repository.findBirthDateByID(any())).thenReturn(Optional.of(birthDate));
        when(repository.findInsightByID(any(), any())).thenReturn(Optional.of(insight));
        when(repository.findAnalyses(any(), eq(AppConst.AI_HISTORY_SIZE))).thenReturn(new ArrayList<>());
        when(analyzeEngine.analyze(any(BirthDate.class), any(), any())).thenReturn(analysisText);
        when(repository.saveAnalysis(any(), any(), anyString())).thenReturn(true);

        Optional<Analysis> result = service.analyzeAndSave(userID, insightID);

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result).get().extracting(Analysis::analysis).isEqualTo(analysisText);
        Assertions.assertThat(result).get().extracting(Analysis::insight).isEqualTo(insight);
        verify(repository).findBirthDateByID(any());
        verify(repository).findInsightByID(any(), any());
        verify(repository).findAnalyses(any(), eq(AppConst.AI_HISTORY_SIZE));
        verify(analyzeEngine).analyze(any(BirthDate.class), any(), any());
        verify(repository).saveAnalysis(any(), any(), anyString());
    }

    @Test
    public void shouldReturnEmptyWhenAnalyzeThrow() {
        String userID = ID.random().getValue();
        String insightID = ID.random().getValue();
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        String insight = "Test insight";

        when(repository.findBirthDateByID(any())).thenReturn(Optional.of(birthDate));
        when(repository.findInsightByID(any(), any())).thenReturn(Optional.of(insight));
        when(repository.findAnalyses(any(), eq(AppConst.AI_HISTORY_SIZE))).thenReturn(new ArrayList<>());
        when(analyzeEngine.analyze(any(BirthDate.class), any(), any())).thenThrow(RuntimeException.class);

        Optional<Analysis> result = service.analyzeAndSave(userID, insightID);

        Assertions.assertThat(result).isEmpty();
        verify(repository).findBirthDateByID(any());
        verify(repository).findInsightByID(any(), any());
        verify(repository).findAnalyses(any(), eq(AppConst.AI_HISTORY_SIZE));
        verify(analyzeEngine).analyze(any(BirthDate.class), any(), any());
        verify(repository, never()).saveAnalysis(any(), any(), anyString());
    }


    @Test
    public void shouldGetQuestion() {
        String userID = ID.random().getValue();
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        String questionText = "Test question";

        when(repository.findBirthDateByID(any())).thenReturn(Optional.of(birthDate));
        when(questionEngine.get(any(BirthDate.class), anyList())).thenReturn(questionText);

        var result = service.generateQuestion(userID);

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result).get().extracting(Question::get).isEqualTo(questionText);
        verify(repository).findBirthDateByID(any());
        verify(questionEngine).get(any(), anyList());
    }

    @Test
    public void shouldReturnEmptyWhenGenerateQuestionThrow() {
        String userID = ID.random().getValue();
        LocalDate birthDate = LocalDate.of(1990, 1, 1);

        when(repository.findBirthDateByID(any())).thenReturn(Optional.of(birthDate));
        when(questionEngine.get(any(BirthDate.class), anyList())).thenThrow(RuntimeException.class);

        var result = service.generateQuestion(userID);

        Assertions.assertThat(result).isEmpty();
        verify(repository).findBirthDateByID(any());
        verify(questionEngine).get(any(), anyList());
    }
}
