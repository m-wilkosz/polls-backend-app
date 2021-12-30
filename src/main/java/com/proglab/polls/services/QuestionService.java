package com.proglab.polls.services;

import java.util.List;
import java.util.Optional;
import java.util.Collection;
import org.joda.time.DateTime;
import com.proglab.polls.entities.Question;

public interface QuestionService {
    Question saveQuestion(Question question);

    void deleteQuestion(Integer id);

    Iterable<Question> listAllQuestions();

    Optional<Question> getById(Integer id);

    Collection<Question> getQuestionsByUserId(Integer id);

    List<Question> getByTextContaining(String keyword);

    List<Question> getByAdded(DateTime date);

    List<Question> getByAddedAfter(DateTime date);
}