package com.denizcanbagdatlioglu.self.insight.domain.service;

import com.denizcanbagdatlioglu.self.insight.domain.entity.Insight;

import java.time.LocalDate;

public class InsightDateValidationService {

    public boolean isInsightDateValid(Insight insight) {
        LocalDate authorsBirthDate = insight.author()
                .birthDate().getDate();

        LocalDate insightCreationDate = insight.date().toLocalDate();

        return insightCreationDate.isAfter(authorsBirthDate);
    }

}
