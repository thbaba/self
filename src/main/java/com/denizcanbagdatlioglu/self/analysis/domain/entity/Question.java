package com.denizcanbagdatlioglu.self.analysis.domain.entity;

public class Question {

    private final String question;

    private Question(String q) {question = q;}

    public Question of(String q) {return new Question(q);}

    public String get() { return question; }

}
