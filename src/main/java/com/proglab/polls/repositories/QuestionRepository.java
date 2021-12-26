package com.proglab.polls.repositories;

import java.util.List;
import org.joda.time.DateTime;
import com.proglab.polls.entities.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Integer> {
    <Optional>Question findById(Integer id);

    //get questions by userID

    List<Question> findByTextContaining(String keyword);

    List<Question> findByAdded(DateTime date);

    List<Question> findByAddedAfter(DateTime date);
}