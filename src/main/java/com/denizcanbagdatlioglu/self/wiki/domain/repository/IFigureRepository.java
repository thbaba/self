package com.denizcanbagdatlioglu.self.wiki.domain.repository;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;

import java.util.List;
import java.util.Optional;

public interface IFigureRepository<T> {

    Optional<T> getFigureByID(ID id);

    List<T> getFiguresWithPagination(int count, int skipCount);

}
