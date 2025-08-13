package com.denizcanbagdatlioglu.self.insight.application;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import com.denizcanbagdatlioglu.self.insight.domain.entity.Insight;
import com.denizcanbagdatlioglu.self.insight.domain.entity.InsightAuthor;
import com.denizcanbagdatlioglu.self.insight.domain.repository.IAuthorRepository;
import com.denizcanbagdatlioglu.self.insight.domain.repository.IInsightRepository;
import com.denizcanbagdatlioglu.self.insight.domain.service.InsightDateValidationService;
import com.denizcanbagdatlioglu.self.insight.domain.usecase.GetInsightUsecase;
import com.denizcanbagdatlioglu.self.insight.domain.usecase.SaveInsightUsecase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class InsightService implements SaveInsightUsecase, GetInsightUsecase {

    private final IInsightRepository insightRepository;

    private final IAuthorRepository authorRepository;

    private final InsightDateValidationService insightDateValidationService;

    public InsightService(IInsightRepository insightRepository, IAuthorRepository authorRepository, InsightDateValidationService dateValidationService) {
        this.insightRepository = insightRepository;
        this.authorRepository = authorRepository;
        this.insightDateValidationService = dateValidationService;
    }

    @Override
    public Optional<Insight> getInsight(ID id) {
        return insightRepository.getInsightById(id);
    }

    @Override
    public List<Insight> getInsights(int pageSize, int page) {
        return insightRepository.getInsights(pageSize, page * pageSize);
    }

    @Override
    public boolean saveInsight(String title, String content, String authorID) {
        Optional<InsightAuthor> maybeAuthor = authorRepository.findAuthorByID(ID.of(authorID));

        return maybeAuthor.map(author -> insight(title, content, author))
                .filter(Insight::checkContentLength)
                .filter(Insight::checkTitleLength)
                .filter(insightDateValidationService::isInsightDateValid)
                .map(insightRepository::saveInsight)
                .orElse(false);
    }

    private Insight insight(String title, String content, InsightAuthor author) {
        return Insight.builder()
                .id(ID.random())
                .title(title)
                .date(LocalDateTime.now())
                .author(author)
                .content(content)
                .build();
    }
}
