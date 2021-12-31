package com.proglab.polls.controllers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Collection;
import org.joda.time.DateTime;
import com.proglab.polls.entities.Question;
import com.proglab.polls.services.QuestionService;
import org.springframework.ui.Model;
import org.springframework.lang.NonNull;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping(value = "/questions", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Question> list(Model model) {return questionService.getAllQuestions();}

    @ApiIgnore
    @DeleteMapping(value = "/questions", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Question> redirect(Model model) {return questionService.getAllQuestions();}

    @PostMapping(value = "/question")
    public ResponseEntity<Question> create(@RequestBody @Validated(Question.class) @NonNull Question question) {
        questionService.saveQuestion(question);
        return ResponseEntity.ok().body(question);
    }

    @PutMapping(value = "/question")
    public ResponseEntity<Void> edit(@RequestBody Question question) {
        Optional<Question> questionFromData = questionService.getById(question.getId());
        if (Objects.nonNull(questionFromData)) {
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

    @GetMapping(value = "/questions/date/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Question> getByAdded(DateTime date) {return questionService.getByAdded(date);}

    @GetMapping(value = "/questions/after_date/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Question> getByAddedAfter(DateTime date) {return questionService.getByAddedAfter(date);}
}