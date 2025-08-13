package com.denizcanbagdatlioglu.self.insight.domain.entity;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.BirthDate;
import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;

import java.time.LocalDate;

public class InsightAuthor {
    
    private final ID id;
    
    private final BirthDate birthDate;
    
    private InsightAuthor(ID id, BirthDate date) {
        this.id = id;
        this.birthDate = date;
    }
    
    public boolean checkBirthDate() {
        return birthDate.getAge() >= 18;
    }

    public BirthDate birthDate() {
        return birthDate;
    }

    public ID id() {
        return id;
    }


    public static final class InsightAuthorBuilder {
        
        private ID id;
        
        private BirthDate birthDate;
        
        private InsightAuthorBuilder() {}
        
        public InsightAuthorBuilder id(String id) {
            this.id = ID.of(id);
            return this;
        }
            
        public InsightAuthorBuilder id(ID id) {
            this.id = id;
            return this;
        }

        public InsightAuthorBuilder birthDate(BirthDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public InsightAuthorBuilder birthDate(LocalDate date) {
            this.birthDate = new BirthDate(date);
            return this;
        }
        
        public InsightAuthor build() throws IllegalStateException {
            if (id == null || birthDate == null)
                throw new IllegalStateException("ID and birthdate must be provided");
            return new InsightAuthor(id, birthDate);
        }
        
    }
    
    public static InsightAuthorBuilder builder() {
        return new InsightAuthorBuilder();
    }
    
}
