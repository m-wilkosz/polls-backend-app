package com.proglab.polls.services;

import java.util.List;
import java.util.Optional;
import org.joda.time.DateTime;
import com.proglab.polls.entities.User;
import org.springframework.stereotype.Service;
import com.proglab.polls.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {return userRepository.save(user);}

    @Override
    public void deleteUser(Integer id) {userRepository.deleteById(id);}

    @Override
    public Optional<User> getUserByUsername(String username) {return userRepository.findByUsername(username);}

    @Override
    public Iterable<User> listAllUsers() {return userRepository.findAll();}

    //count questions by ID

    //get questions by ID

    @Override
    public List<User> getLastActiveUsersAfter(DateTime date) {return userRepository.findByLastActiveAfter(date);}

    @Override
    public Integer getNumberOfUsersJoinedAfter(DateTime date) {return userRepository.countJoinedAfterDate(date);}

    //get most active user
}