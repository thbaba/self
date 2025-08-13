package com.denizcanbagdatlioglu.self.wiki.controller.mapper;

import com.denizcanbagdatlioglu.self.wiki.controller.dto.FigureBriefResponse;
import com.denizcanbagdatlioglu.self.wiki.controller.dto.FigureResponse;
import com.denizcanbagdatlioglu.self.wiki.domain.entity.Figure;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FigureMapper {

    @Mapping(target = "id", expression = "java(figure.id().getValue())")
    @Mapping(target = "name", expression = "java(figure.name())")
    @Mapping(target = "brief", expression = "java(figure.brief())")
    public FigureBriefResponse toBriefResponse(Figure figure);

    @Mapping(target = "id", expression = "java(figure.id().getValue())")
    @Mapping(target = "name", expression = "java(figure.name())")
    @Mapping(target = "brief", expression = "java(figure.brief())")
    @Mapping(target = "description", expression = "java(figure.description())")
    public FigureResponse toResponse(Figure figure);


}
