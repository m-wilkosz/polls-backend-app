package com.proglab.polls.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Collection;
import org.joda.time.DateTime;
import com.proglab.polls.entities.User;
import com.proglab.polls.entities.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    @Query("select count(q) from User u join u.questions q where u.id = ?1")
    Integer countQuestionsById(Integer id);

    @Query("select q from User u join u.questions q where u.id = ?1")
    Collection<Question> findQuestionsById(Integer id);

    List<User> findByLastActiveAfter(DateTime date);

    @Query("select count(u) from User u where u.joiningDate > :date")
    Integer countJoinedAfterDate(@Param("date") DateTime date);
}