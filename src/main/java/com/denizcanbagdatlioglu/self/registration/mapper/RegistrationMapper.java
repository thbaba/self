package com.denizcanbagdatlioglu.self.registration.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;

import com.denizcanbagdatlioglu.self.user.domain.entity.User;
import com.denizcanbagdatlioglu.self.user.domain.valueobject.BirthDate;
import com.denizcanbagdatlioglu.self.user.domain.valueobject.Gender;
import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import com.denizcanbagdatlioglu.self.registration.dto.UserRegistrationRequest;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {

    @Mapping(target = "id", expression = "java(generateID())")
    @Mapping(target = "birthDate", source = "birthDate", qualifiedByName = "localDateToBirthDate")
    @Mapping(target = "gender", source = "gender", qualifiedByName = "genderStringToGenderEnum")
    User toUser(UserRegistrationRequest request);

    default ID generateID() {
        return ID.random();
    }

    @Named("localDateToBirthDate")
    default BirthDate localDateToBirthDate(LocalDate date) {
        return new BirthDate(date);
    }

    @Named("genderStringToGenderEnum")
    default Gender genderStringToGenderEnum(String gender) {
        return Gender.valueOf(gender);
    }

}
