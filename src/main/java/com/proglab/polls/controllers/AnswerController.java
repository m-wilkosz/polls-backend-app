package com.proglab.polls.controllers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Collection;
import com.proglab.polls.entities.Answer;
import com.proglab.polls.services.AnswerService;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api")
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @GetMapping(value = "/answers", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Answer> list(Model model) {return answerService.getAllAnswers();}

    @ApiIgnore
    @DeleteMapping(value = "/answers", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Answer> redirect(Model model) {return answerService.getAllAnswers();}

    @PostMapping(value = "/answer")
    public ResponseEntity<Answer> create(@RequestBody @Validated(Answer.class) @NonNull Answer answer) {
        answerService.saveAnswer(answer);
        return ResponseEntity.ok().body(answer);
    }

    @PutMapping(value = "/answer/{id}")
    public ResponseEntity<Void> edit(@RequestBody Answer answer, @PathVariable Integer id) {
        Optional<Answer> answerFromData = answerService.getById(id);
        if (Objects.nonNull(answerFromData)) {
            answerFromData.get().setText(answer.getText());
            answerService.saveAnswer(answer);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/answer/{id}")
    public RedirectView delete(HttpServletResponse response, @PathVariable Integer id) {
        answerService.deleteAnswer(id);
        return new RedirectView("/api/answers", true);
    }

    @GetMapping(value = "/answer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Answer> getById(@PathVariable Integer id) {return answerService.getById(id);}

    @GetMapping(value = "/answers/keyword/{keyword}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Answer> getByTextContaining(@PathVariable String keyword) {return answerService.getByTextContaining(keyword);}

    @GetMapping(value = "/answers/question-id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Answer> getByQuestionId(@PathVariable Integer id) {return answerService.getByQuestionId(id);}
}