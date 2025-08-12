package com.denizcanbagdatlioglu.self.user.domain.repository;

import java.util.Optional;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import com.denizcanbagdatlioglu.self.user.domain.entity.User;

public interface IUserRepository {

    Optional<User> findUserByID(ID id);

    Optional<User> registerUser(User user);

}
