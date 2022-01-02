package com.proglab.polls.services;

import java.util.List;
import java.util.Optional;
import java.util.Collection;
import org.joda.time.DateTime;
import com.proglab.polls.entities.Question;
import org.springframework.stereotype.Service;

@Service
public interface QuestionService {
    Question saveQuestion(Question question);

    void deleteQuestion(Integer id);

    Iterable<Question> getAllQuestions();

    Optional<Question> getById(Integer id);

    Collection<Question> getByUserId(Integer id);

    List<Question> getByTextContaining(String keyword);

    List<Question> getByAdded(DateTime date1, DateTime date2);

    List<Question> getByAddedAfter(DateTime date);
}