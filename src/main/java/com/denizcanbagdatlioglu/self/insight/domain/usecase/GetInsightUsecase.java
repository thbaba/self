package com.denizcanbagdatlioglu.self.insight.domain.usecase;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import com.denizcanbagdatlioglu.self.insight.domain.entity.Insight;

import java.util.List;
import java.util.Optional;

public interface GetInsightUsecase {

    Optional<Insight> getInsight(ID id);

    List<Insight> getInsights(int pageSize, int page);

}
