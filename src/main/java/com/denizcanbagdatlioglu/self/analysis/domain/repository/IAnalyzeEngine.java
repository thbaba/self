package com.denizcanbagdatlioglu.self.analysis.domain.repository;

import com.denizcanbagdatlioglu.self.analysis.domain.entity.Analysis;
import com.denizcanbagdatlioglu.self.common.domain.valueobject.BirthDate;

import java.util.List;

public interface IAnalyzeEngine {

    String analyze(BirthDate birthDate, String insight, List<Analysis> analyses);

    String get(BirthDate birthDate, String insight, Analysis... analyses);

}
