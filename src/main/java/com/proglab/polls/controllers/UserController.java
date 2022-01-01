package com.proglab.polls.controllers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Collection;
import com.proglab.polls.entities.User;
import com.proglab.polls.entities.Question;
import com.proglab.polls.entities.Answer;
import com.proglab.polls.services.UserService;
import com.proglab.polls.services.QuestionService;
import com.proglab.polls.services.AnswerService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.ui.Model;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<User> list(Model model) {return userService.getAllUsers();}

    @ApiIgnore
    @DeleteMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<User> redirect(Model model) {return userService.getAllUsers();}

    @PostMapping(value = "/user")
    public ResponseEntity<User> create(@RequestBody @Validated(User.class) @NonNull User user) {
        userService.saveUser(user);
        Collection<Question> uQuestions = user.getQuestions();
        for (Question uQ : uQuestions) {
            uQ.setUser(user);
            questionService.saveQuestion(uQ);
            Collection<Answer> uAnswers = uQ.getAnswers();
            for (Answer uA : uAnswers) {
                uA.setQuestion(uQ);
                answerService.saveAnswer(uA);
            }
        }
        return ResponseEntity.ok().body(user);
    }

    @PutMapping(value = "/user/{id}")
    public ResponseEntity<Void> edit(@RequestBody User user, @PathVariable Integer id) {
        Optional<User> userFromData = userService.getById(id);
        if (Objects.nonNull(userFromData)) {
            userFromData.get().setUsername(user.getUsername());
            userFromData.get().setEmailAddress(user.getEmailAddress());
            userFromData.get().setJoiningDate(user.getJoiningDate());
            userFromData.get().setLastActive(user.getLastActive());
            userService.saveUser(userFromData.get());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/user/{id}")
    public RedirectView delete(HttpServletResponse response, @PathVariable Integer id) {
        userService.deleteUser(id);
        return new RedirectView("/api/users", true);
    }

    @GetMapping(value = "/user/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> getByUsername(@PathVariable String username) {return userService.getByUsername(username);}

    @GetMapping(value = "/user/{id}/number-of-questions", produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer getNumOfQuestionsById(@PathVariable Integer id) {return userService.getNumOfQuestionsById(id);}

    @GetMapping(value = "/user/{id}/questions", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Question> getQuestionsById(@PathVariable Integer id) {return userService.getQuestionsById(id);}

    @GetMapping(value = "/users/active-after/{d}-{m}-{y}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getActiveAfter(@PathVariable String d, @PathVariable String m, @PathVariable String y) {
        DateTime date = DateTime.parse(d+"/"+m+"/"+y+" 00:00:00", DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss"));
        return userService.getActiveAfter(date);
    }

    @GetMapping(value = "/users/number-of-joined-after/{d}-{m}-{y}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer getNumOfJoinedAfter(@PathVariable String d, @PathVariable String m, @PathVariable String y) {
        DateTime date = DateTime.parse(d+"/"+m+"/"+y+" 00:00:00", DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss"));
        return userService.getNumOfJoinedAfter(date);
    }

    @GetMapping(value = "/users/most-active", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getMostActive() {return userService.getMostActive();}
}