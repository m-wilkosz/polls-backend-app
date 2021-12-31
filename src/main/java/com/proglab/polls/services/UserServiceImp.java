package com.proglab.polls.services;

import java.util.List;
import java.util.Optional;
import java.util.Collection;
import org.joda.time.DateTime;
import com.proglab.polls.entities.User;
import com.proglab.polls.entities.Question;
import com.proglab.polls.repositories.UserRepository;
import org.springframework.stereotype.Service;
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
    public Optional<User> getByUsername(String username) {return userRepository.findByUsername(username);}

    @Override
    public Iterable<User> getAllUsers() {return userRepository.findAll();}

    @Override
    public Integer getNumOfQuestionsById(Integer id) {return userRepository.countQuestionsById(id);};

    @Override
    public Collection<Question> getQuestionsById(Integer id) {return userRepository.findQuestionsById(id);};

    @Override
    public List<User> getActiveAfter(DateTime date) {return userRepository.findActiveAfter(date);}

    @Override
    public Integer getNumOfJoinedAfter(DateTime date) {return userRepository.countJoinedAfter(date);}

    @Override
    public User getMostActive() {
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