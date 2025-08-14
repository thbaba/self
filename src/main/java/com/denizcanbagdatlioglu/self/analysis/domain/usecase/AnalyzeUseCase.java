package com.denizcanbagdatlioglu.self.analysis.domain.usecase;

import com.denizcanbagdatlioglu.self.analysis.domain.entity.Analysis;

import java.util.Optional;

public interface AnalyzeUseCase {

    Optional<Analysis> analyzeAndSave(String userID, String insightID);

}
