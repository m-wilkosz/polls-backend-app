package com.proglab.polls.controllers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Collection;
import com.proglab.polls.entities.Answer;
import com.proglab.polls.services.AnswerService;
import com.proglab.polls.entities.Question;
import com.proglab.polls.services.QuestionService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.lang.NonNull;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletResponse;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @GetMapping(value = "/questions", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Question> list(Model model) {return questionService.getAllQuestions();}

    @ApiIgnore
    @DeleteMapping(value = "/questions", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Question> redirect(Model model) {return questionService.getAllQuestions();}

    @PostMapping(value = "/question")
    public ResponseEntity<Question> create(@RequestBody @Validated(Question.class) @NonNull Question question) {
        questionService.saveQuestion(question);
        Collection<Answer> answers = question.getAnswers();
        for (Answer a : answers) {
            a.setQuestion(question);
            answerService.saveAnswer(a);
        }
        return ResponseEntity.ok().body(question);
    }

    @PutMapping(value = "/question/{id}")
    public ResponseEntity<Void> edit(@RequestBody Question question, @PathVariable Integer id) {
        Optional<Question> questionFromData = questionService.getById(id);
        if (Objects.nonNull(questionFromData)) {
            questionFromData.get().setText(question.getText());
            questionFromData.get().setAdded(question.getAdded());
            questionService.saveQuestion(question);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/question/{id}")
    public RedirectView delete(HttpServletResponse response, @PathVariable Integer id) {
        questionService.deleteQuestion(id);
        return new RedirectView("/api/questions", true);
    }

    @GetMapping(value = "/question/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Question> getById(@PathVariable Integer id) {return questionService.getById(id);}

    @GetMapping(value = "/questions/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Question> getByUserId(@PathVariable Integer id) {return questionService.getByUserId(id);}

    @GetMapping(value = "/questions/keyword/{keyword}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Question> getByTextContaining(@PathVariable String keyword) {return questionService.getByTextContaining(keyword);}

    @GetMapping(value = "/questions/date/{d}-{m}-{y}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Question> getByAdded(@PathVariable String d, @PathVariable String m, @PathVariable String y) {
        DateTime date1 = DateTime.parse(d+"/"+m+"/"+y+" 00:00:00", DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss"));
        DateTime date2 = date1.plusDays(1);
        return questionService.getByAdded(date1, date2);
    }

    @GetMapping(value = "/questions/after-date/{d}-{m}-{y}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Question> getByAddedAfter(@PathVariable String d, @PathVariable String m, @PathVariable String y) {
        DateTime date = DateTime.parse(d+"/"+m+"/"+y+" 00:00:00", DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss"));
        return questionService.getByAddedAfter(date);
    }
}