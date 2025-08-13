package com.denizcanbagdatlioglu.self.analysis.domain.usecase;

import com.denizcanbagdatlioglu.self.analysis.domain.entity.Analysis;

import java.util.Optional;

public interface GetAnalysisUseCase {

    Optional<Analysis> getAnalysis(String userID, String insightID);

}
