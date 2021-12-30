package com.proglab.polls.controllers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Collection;
import org.joda.time.DateTime;
import org.springframework.ui.Model;
import com.proglab.polls.entities.User;
import org.springframework.lang.NonNull;
import org.springframework.http.MediaType;
import com.proglab.polls.entities.Question;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpServletResponse;
import com.proglab.polls.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<User> list(Model model) {return userService.listAllUsers();}

    @ApiIgnore
    @DeleteMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<User> redirect(Model model) {return userService.listAllUsers();}

    @PostMapping(value = "/user")
    public ResponseEntity<User> create(@RequestBody @Validated(User.class) @NonNull User user) {
        userService.saveUser(user);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping(value = "/user")
    public ResponseEntity<Void> edit(@RequestBody User user) {
        Optional<User> userFromData = userService.getUserByUsername(user.getUsername());
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
    public Optional<User> getByUsername(@PathVariable String username) {return userService.getUserByUsername(username);}

    @GetMapping(value = "/user/{id}/number_of_questions", produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer getNumOfQuestions(@PathVariable Integer id) {return userService.getNumberOfQuestionsByUserId(id);}

    @GetMapping(value = "/user/{id}/questions", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Question> getQuestionsByUserId(@PathVariable Integer id) {return userService.getQuestionsByUserId(id);}

    @GetMapping(value = "/users/active_after_{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsersActiveAfter(@PathVariable DateTime date) {return userService.getLastActiveUsersAfter(date);}

    @GetMapping(value = "/users/number_of_joined_after_{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer getNumOfUsersJoinedAfter(@PathVariable DateTime date) {return userService.getNumberOfUsersJoinedAfter(date);}

    @GetMapping(value = "/users/most_active", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getMostActive() {return userService.getMostActiveUser();}
}