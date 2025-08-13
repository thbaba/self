package com.denizcanbagdatlioglu.self.insight.domain.repository;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import com.denizcanbagdatlioglu.self.insight.domain.entity.Insight;

import java.util.List;
import java.util.Optional;

public interface IInsightRepository {

    public boolean saveInsight(Insight insight);

    public Optional<Insight> getInsightById(ID id);

    public List<Insight> getInsights(int count, int skip);

}
