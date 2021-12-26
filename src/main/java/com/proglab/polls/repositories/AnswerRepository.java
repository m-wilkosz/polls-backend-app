package com.proglab.polls.repositories;

import java.util.List;
import com.proglab.polls.entities.Answer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer, Integer> {
    <Optional>Answer findById(Integer id);

    List<Answer> findByTextContaining(String keyword);

    //find by questionID
}