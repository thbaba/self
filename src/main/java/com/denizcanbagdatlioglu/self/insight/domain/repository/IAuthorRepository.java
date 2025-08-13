package com.denizcanbagdatlioglu.self.insight.domain.repository;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import com.denizcanbagdatlioglu.self.insight.domain.entity.InsightAuthor;

import java.util.Optional;

public interface IAuthorRepository {

    Optional<InsightAuthor> findAuthorByID(ID id);

}
