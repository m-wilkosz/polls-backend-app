package com.proglab.polls.repositories;

import java.util.List;
import java.util.Collection;
import com.proglab.polls.entities.Answer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer, Integer> {
    List<Answer> findByTextContaining(String keyword);

    @Query("select a from Question q join q.answers a where q.id = ?1")
    Collection<Answer> findAnswersByQuestionId(Integer id);
}