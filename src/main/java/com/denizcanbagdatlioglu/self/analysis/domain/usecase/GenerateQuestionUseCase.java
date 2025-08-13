package com.denizcanbagdatlioglu.self.analysis.domain.usecase;

import com.denizcanbagdatlioglu.self.analysis.domain.entity.Question;

import java.util.Optional;

public interface GenerateQuestionUseCase {

    Optional<Question> generateQuestion(String userID);

}
