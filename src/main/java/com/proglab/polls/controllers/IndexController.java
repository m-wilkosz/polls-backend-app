package com.proglab.polls.controllers;

import org.joda.time.DateTime;
import com.proglab.polls.entities.User;
import org.springframework.http.MediaType;
import com.proglab.polls.entities.Question;
import com.proglab.polls.services.UserService;
import com.proglab.polls.services.QuestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/")
public class IndexController {
    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @GetMapping(value = "")
    String index() {return "index";}

    @PostMapping(value = "generate", produces = MediaType.TEXT_PLAIN_VALUE)
    public String generate() {
        DateTime date = DateTime.now();

        Question q1 = new Question("how many hours do you sleep?", date);
        Question q2 = new Question("where are you from?", date);
        Question q3 = new Question("what is your favourite color?", date);

        User u1 = new User("user1", "mw98@gmail.com", date.minusDays(10), date.minusDays(5));

        u1.getQuestions().add(q1);
        u1.getQuestions().add(q2);
        u1.getQuestions().add(q3);

        userService.saveUser(u1);

        q1.setUser(u1);
        q2.setUser(u1);
        q3.setUser(u1);

        questionService.saveQuestion(q1);
        questionService.saveQuestion(q2);
        questionService.saveQuestion(q3);

        return "generated";
    }
}