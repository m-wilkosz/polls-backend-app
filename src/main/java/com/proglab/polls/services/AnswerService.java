package com.proglab.polls.services;

import java.util.List;
import java.util.Optional;
import com.proglab.polls.entities.Answer;

public interface AnswerService {
    Answer saveAnswer(Answer answer);

    void deleteAnswer(Integer id);

    Iterable<Answer> listAllAnswers();

    Optional<Answer> getByID(Integer id);

    List<Answer> getByTextContaining(String keyword);

    //get by questionID
}