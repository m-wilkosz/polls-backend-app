package com.proglab.polls.repositories;

import java.util.List;
import com.proglab.polls.entities.Answer;
import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer, Integer> {
    List<Answer> findByTextContaining(String keyword);

    //find by questionID
}