package com.proglab.polls.controllers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Collection;
import org.joda.time.DateTime;
import com.proglab.polls.entities.User;
import com.proglab.polls.entities.Question;
import com.proglab.polls.services.UserService;
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

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<User> list(Model model) {return userService.getAllUsers();}

    @ApiIgnore
    @DeleteMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<User> redirect(Model model) {return userService.getAllUsers();}

    @PostMapping(value = "/user")
    public ResponseEntity<User> create(@RequestBody @Validated(User.class) @NonNull User user) {
        userService.saveUser(user);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping(value = "/user")
    public ResponseEntity<Void> edit(@RequestBody User user) {
        Optional<User> userFromData = userService.getByUsername(user.getUsername());
        if (Objects.nonNull(userFromData)) {
            userService.saveUser(user);
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

    @GetMapping(value = "/user/{id}/number_of_questions", produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer getNumOfQuestionsById(@PathVariable Integer id) {return userService.getNumOfQuestionsById(id);}

    @GetMapping(value = "/user/{id}/questions", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Question> getQuestionsById(@PathVariable Integer id) {return userService.getQuestionsById(id);}

    @GetMapping(value = "/users/active_after/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getActiveAfter(@PathVariable DateTime date) {return userService.getActiveAfter(date);}

    @GetMapping(value = "/users/number_of_joined_after/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer getNumOfJoinedAfter(@PathVariable DateTime date) {return userService.getNumOfJoinedAfter(date);}

    @GetMapping(value = "/users/most_active", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getMostActive() {return userService.getMostActive();}
}