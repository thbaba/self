package com.denizcanbagdatlioglu.self.auth.domain.entity;

import com.denizcanbagdatlioglu.self.auth.domain.valueobject.BirthDate;
import com.denizcanbagdatlioglu.self.auth.domain.valueobject.Gender;
import com.denizcanbagdatlioglu.self.auth.domain.valueobject.Password;
import com.denizcanbagdatlioglu.self.auth.domain.valueobject.Username;
import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;

public class User {

    private final ID id;

    private final Username username;

    private final Password password;

    private final BirthDate birthDate;

    private final Gender gender;

    public boolean isAdult() {
        return birthDate.getAge() >= 18;
    }

    private User(ID id, Username username, Password password,
                 BirthDate birthDate, Gender gender) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public ID id() {
        return id;
    }

    public Username username() {
        return username;
    }

    public Password password() {
        return password;
    }

    public BirthDate birthDate() {
        return birthDate;
    }

    public Gender gender() {
        return gender;
    }

    public static class UserBuilder {
        private ID id;
        private Username username;
        private Password password;
        private BirthDate birthDate;
        private Gender gender;

        private UserBuilder() {}

        public UserBuilder id(ID id) {
            this.id = id;
            return this;
        }

        public UserBuilder username(Username username) {
            this.username = username;
            return this;
        }

        public UserBuilder password(Password password) {
            this.password = password;
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
            return new User(id, username, password, birthDate, gender);
        }
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }
}
