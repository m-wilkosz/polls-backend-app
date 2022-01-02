package com.proglab.polls.repositories;

import java.util.List;
import java.util.Collection;
import org.joda.time.DateTime;
import com.proglab.polls.entities.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Integer> {
    @Query("select q from User u join u.questions q where u.id = ?1")
    Collection<Question> findByUserId(Integer id);

    List<Question> findByTextContaining(String keyword);

    @Query("select q from Question q where q.added > :date1 and q.added < :date2")
    List<Question> findByAdded(@Param("date1") DateTime date1, @Param("date2") DateTime date2);

    List<Question> findByAddedAfter(DateTime date);
}