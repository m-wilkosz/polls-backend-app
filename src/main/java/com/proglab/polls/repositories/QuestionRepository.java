package com.proglab.polls.repositories;

import java.util.List;
import java.util.Collection;
import org.joda.time.DateTime;
import com.proglab.polls.entities.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Integer> {
    @Query("select q from User u join u.questions q where u.id = ?1")
    Collection<Question> findQuestionsByUserId(Integer id);

    List<Question> findByTextContaining(String keyword);

    List<Question> findByAdded(DateTime date);

    List<Question> findByAddedAfter(DateTime date);
}