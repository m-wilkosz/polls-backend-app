package com.proglab.polls.controllers;

import java.util.Objects;
import java.util.Optional;
import org.springframework.ui.Model;
import com.proglab.polls.entities.User;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import javax.validation.constraints.NotNull;
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
    public ResponseEntity<User> create(@RequestBody @Validated(User.class) @NotNull User user) {
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

    @GetMapping(value = "/user/{username}")
    public Optional<User> getByUsername(@PathVariable String username) {return userService.getUserByUsername(username);}
}