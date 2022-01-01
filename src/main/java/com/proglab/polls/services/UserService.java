package com.proglab.polls.services;

import java.util.List;
import java.util.Optional;
import java.util.Collection;
import org.joda.time.DateTime;
import com.proglab.polls.entities.User;
import com.proglab.polls.entities.Question;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User saveUser(User user);

    void deleteUser(Integer id);

    Optional<User> getById(Integer id);

    Optional<User> getByUsername(String username);

    Iterable<User> getAllUsers();

    Integer getNumOfQuestionsById(Integer id);

    Collection<Question> getQuestionsById(Integer id);

    List<User> getActiveAfter(DateTime date);

    Integer getNumOfJoinedAfter(DateTime date);

    User getMostActive();
}