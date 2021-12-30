package com.proglab.polls.services;

import java.util.List;
import java.util.Optional;
import java.util.Collection;
import org.joda.time.DateTime;
import com.proglab.polls.entities.User;
import com.proglab.polls.entities.Question;

public interface UserService {
    User saveUser(User user);

    void deleteUser(Integer id);

    Optional<User> getUserByUsername(String username);

    Iterable<User> listAllUsers();

    Integer getNumberOfQuestionsByUserId(Integer id);

    Collection<Question> getQuestionsByUserId(Integer id);

    List<User> getLastActiveUsersAfter(DateTime date);

    Integer getNumberOfUsersJoinedAfter(DateTime date);

    User getMostActiveUser();
}