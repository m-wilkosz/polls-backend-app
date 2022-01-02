package com.proglab.polls.repositories;

import java.util.List;
import org.joda.time.DateTime;
import com.proglab.polls.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);

    //count questions by ID

    //get questions by ID

    List<User> findByLastActiveAfter(DateTime date);

    @Query("select count(u) from User u where u.joiningDate > :date")
    Integer countJoinedAfterDate(@Param("date") DateTime date);
}