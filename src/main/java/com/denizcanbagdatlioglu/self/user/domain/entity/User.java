package com.denizcanbagdatlioglu.self.user.domain.entity;

import java.util.Objects;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;
import com.denizcanbagdatlioglu.self.common.domain.valueobject.BirthDate;
import com.denizcanbagdatlioglu.self.user.domain.valueobject.Gender;

public class User {
    
    private final ID id;

    private final BirthDate birthDate;

    private final Gender gender;

    private User(ID id, BirthDate birthDate, Gender gender) {
        this.id = id;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public ID id() {
        return id;
    }

    public BirthDate birthDate() {
        return birthDate;
    }

    public Gender gender() {
        return gender;
    }

    public boolean isAdult() {
        return birthDate().getAge() >= 18;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof User otherUser)) return false;
        return this.id.equals(otherUser.id()) && 
            this.birthDate.equals(otherUser.birthDate()) && 
            this.gender.equals(otherUser.gender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, birthDate, gender);
    }

    public static class UserBuilder {
        private ID id;

        private BirthDate birthDate;

        private Gender gender;

        private UserBuilder() {}

        public UserBuilder id(ID id) {
            this.id = id;
            return this;
        }

        public UserBuilder birthDate(BirthDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public UserBuilder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public User build() {
            if(id == null || birthDate == null || gender == null) {
                throw new IllegalStateException("ID, BirthDate and Gender can not be null.");
            }
            return new User(id, birthDate, gender);
        }
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

}
