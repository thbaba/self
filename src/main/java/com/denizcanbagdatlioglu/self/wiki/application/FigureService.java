package com.denizcanbagdatlioglu.self.wiki.application;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import com.denizcanbagdatlioglu.self.wiki.domain.entity.Figure;
import com.denizcanbagdatlioglu.self.wiki.domain.repository.IFigureRepository;
import com.denizcanbagdatlioglu.self.wiki.domain.usecase.ListFigureUseCase;

import java.util.List;
import java.util.Optional;

public class FigureService<T extends Figure> implements ListFigureUseCase<T> {

    private final IFigureRepository<T> repository;

    public FigureService(IFigureRepository<T> repository) {
        this.repository = repository;
    }

    @Override
    public Optional<T> getFigure(ID id) {
        return repository.getFigureByID(id);
    }

    @Override
    public List<T> getFigures(int count, int stepOver) {
        return repository.getFiguresWithPagination(count, count * stepOver);
    }

}
