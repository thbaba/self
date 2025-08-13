package com.denizcanbagdatlioglu.self.wiki.domain.usecase;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import com.denizcanbagdatlioglu.self.wiki.domain.entity.Figure;

import java.util.List;
import java.util.Optional;

public interface ListFigureUseCase<T extends Figure> {

    Optional<T> getFigure(ID id);

    /**
     * @param count how many entries in a result set
     * @param stepOver how many results set to skip?
     * @return count times figure from db
     * */
    List<T> getFigures(int count, int stepOver);

}
