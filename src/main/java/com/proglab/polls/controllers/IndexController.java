package com.proglab.polls.controllers;

import org.joda.time.DateTime;
import com.proglab.polls.entities.User;
import org.springframework.http.MediaType;
import com.proglab.polls.services.UserService;
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

    @GetMapping(value = "")
    String index() {return "index";}

    @PostMapping(value = "generate", produces = MediaType.TEXT_PLAIN_VALUE)
    public String generate() {
        DateTime date = DateTime.now();

        User user1 = new User("user1", "mw98@gmail.com", date.minusDays(10), date.minusDays(5));
        User user2 = new User("user2", "adam_m@gmail.com", date.minusDays(7), date.minusDays(4));
        User user3 = new User("user3", "j_smith@gmail.com", date.minusDays(1), date);

        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);

        return "generated";
    }
}