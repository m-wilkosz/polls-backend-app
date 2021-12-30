package com.proglab.polls.services;

import java.util.List;
import java.util.Optional;
import java.util.Collection;
import org.joda.time.DateTime;
import com.proglab.polls.entities.User;
import com.proglab.polls.entities.Question;
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

    @Override
    public Integer getNumberOfQuestionsByUserId(Integer id) {return userRepository.countQuestionsById(id);};

    @Override
    public Collection<Question> getQuestionsByUserId(Integer id) {return userRepository.findQuestionsById(id);};

    @Override
    public List<User> getLastActiveUsersAfter(DateTime date) {return userRepository.findByLastActiveAfter(date);}

    @Override
    public Integer getNumberOfUsersJoinedAfter(DateTime date) {return userRepository.countJoinedAfterDate(date);}

    @Override
    public User getMostActiveUser() {
        Integer max = 0, n = 0;
        User uMax = null;
        Iterable<User> users = userRepository.findAll();
        for (User u : users) {
            n = userRepository.countQuestionsById(u.getId());
            if (n > max) {
                max = n;
                uMax = u;
            }
        }
        return uMax;
    }
}