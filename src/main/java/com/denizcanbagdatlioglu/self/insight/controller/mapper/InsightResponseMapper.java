package com.denizcanbagdatlioglu.self.insight.controller.mapper;

import com.denizcanbagdatlioglu.self.insight.controller.dto.InsightResponse;
import com.denizcanbagdatlioglu.self.insight.domain.entity.Insight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.time.format.DateTimeFormatter;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InsightResponseMapper {

//    String id, String title, String date, String author
    @Mapping(target = "id", expression = "java(extractID(insight))")
    @Mapping(target = "title", expression = "java(extractTitle(insight))")
    @Mapping(target = "date", expression = "java(extractDate(insight))")
    @Mapping(target = "author", expression = "java(extractAuthor(insight))")
    InsightResponse toResponse(Insight insight);

    default String extractID(Insight insight) {
        return insight.id().getValue();
    }

    default String extractTitle(Insight insight) {
        return insight.title();
    }

    default String extractDate(Insight insight) {
        return insight.date().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    default String extractAuthor(Insight insight) {
        return insight.author().id().getValue();
    }

}
