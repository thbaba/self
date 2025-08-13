package com.denizcanbagdatlioglu.self.insight.domain.usecase;

public interface SaveInsightUsecase {

    boolean saveInsight(String title, String content, String authorID);

}
