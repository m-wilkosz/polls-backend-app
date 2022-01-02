package com.proglab.polls.controllers;

import com.proglab.polls.entities.Answer;
import com.proglab.polls.services.AnswerService;
import org.joda.time.DateTime;
import com.proglab.polls.entities.User;
import com.proglab.polls.entities.Question;
import com.proglab.polls.services.UserService;
import com.proglab.polls.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/")
public class IndexController {
    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @GetMapping(value = "")
    public String index() {return "index";}

    @PostMapping(value = "generate", produces = MediaType.TEXT_PLAIN_VALUE)
    public String generate() {
        DateTime date = DateTime.now();

        User u1 = new User("user1", "mw98@gmail.com", date.minusDays(10), date.minusDays(5));

        Question q1 = new Question("how many hours do you sleep?", date);
        Question q2 = new Question("what is your favourite color?", date);

        Answer a11 = new Answer("7");
        Answer a12 = new Answer("8");
        Answer a21 = new Answer("black");
        Answer a22 = new Answer("blue");

        u1.getQuestions().add(q1);
        u1.getQuestions().add(q2);

        userService.saveUser(u1);

        q1.setUser(u1);
        q2.setUser(u1);
        q1.getAnswers().add(a11);
        q1.getAnswers().add(a12);
        q2.getAnswers().add(a21);
        q2.getAnswers().add(a22);

        questionService.saveQuestion(q1);
        questionService.saveQuestion(q2);

        a11.setQuestion(q1);
        a12.setQuestion(q1);
        a21.setQuestion(q2);
        a22.setQuestion(q2);

        answerService.saveAnswer(a11);
        answerService.saveAnswer(a12);
        answerService.saveAnswer(a21);
        answerService.saveAnswer(a22);

        return "generated";
    }
}