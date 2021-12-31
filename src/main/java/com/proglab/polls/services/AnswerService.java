package com.proglab.polls.services;

import java.util.List;
import java.util.Optional;
import java.util.Collection;
import com.proglab.polls.entities.Answer;
import org.springframework.stereotype.Service;

@Service
public interface AnswerService {
    Answer saveAnswer(Answer answer);

    void deleteAnswer(Integer id);

    Iterable<Answer> getAllAnswers();

    Optional<Answer> getByID(Integer id);

    List<Answer> getByTextContaining(String keyword);

    Collection<Answer> getByQuestionId(Integer id);
}