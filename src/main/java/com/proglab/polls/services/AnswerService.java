package com.proglab.polls.services;

import java.util.List;
import com.proglab.polls.entities.Answer;

public interface AnswerService {
    Answer saveAnswer(Answer answer);

    void deleteAnswer();

    Iterable<Answer> listAllAnswers();

    <Optional>Answer getByID(Integer id);

    List<Answer> getByTextContaining(String keyword);

    //get by questionID
}