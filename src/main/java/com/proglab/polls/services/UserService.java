package com.proglab.polls.services;

import java.util.List;
import org.joda.time.DateTime;
import com.proglab.polls.entities.User;

public interface UserService {
    User saveUser(User user);

    void deleteUser(Integer id);

    <Optional>User getUserByUsername(String username);

    Iterable<User> listAllUsers();

    //count questions by ID

    //get questions by ID

    List<User> getLastActiveUsersAfter(DateTime date);

    Integer getNumberOfUsersJoinedAfter(DateTime date);

    //get most active user
}