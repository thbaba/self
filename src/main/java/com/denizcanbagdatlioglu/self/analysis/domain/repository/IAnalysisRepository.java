package com.denizcanbagdatlioglu.self.analysis.domain.repository;

import com.denizcanbagdatlioglu.self.analysis.domain.entity.Analysis;
import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IAnalysisRepository {

    Optional<String> findInsightByID(ID userID, ID insightID);

    Optional<LocalDate> findBirthDateByID(ID userID);

    Optional<Analysis> findAnalysisByInsightID(ID userID, ID insightID);

    List<Analysis> findAnalyses(ID userID, int analysesCount);

    boolean saveAnalysis(ID userID, ID insightID, String analysis);

}
