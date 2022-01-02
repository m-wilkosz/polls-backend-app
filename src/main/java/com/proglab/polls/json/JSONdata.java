package com.proglab.polls.json;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import com.proglab.polls.entities.User;
import com.proglab.polls.services.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.beans.factory.annotation.Autowired;

public class JSONdata {
    @Autowired
    private UserService userService;

    public void importFile(String filepath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JodaModule());
        Collection<User> users = objectMapper.readValue(new File(filepath), new TypeReference<Collection<User>>(){});
        for (User user : users) {
            userService.saveUser(user);
        }
    }
}