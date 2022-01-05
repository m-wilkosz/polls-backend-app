package com.proglab.polls.json;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import com.proglab.polls.entities.User;
import com.proglab.polls.entities.Question;
import com.proglab.polls.entities.Answer;
import com.proglab.polls.services.UserService;
import com.proglab.polls.services.QuestionService;
import com.proglab.polls.services.AnswerService;
import org.springframework.context.ApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.core.type.TypeReference;

public class JSONdata {
    private final UserService userService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JodaModule())
            .configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    public void importUsersFromFile(String filepath) {
        try {
            Collection<User> users = objectMapper.readValue(new File(filepath), new TypeReference<Collection<User>>(){});
            for (User user : users) {
                userService.saveUser(user);
                Collection<Question> questions = user.getQuestions();
                for (Question question : questions) {
                    questionService.saveQuestion(question);
                    Collection<Answer> answers = question.getAnswers();
                    for (Answer answer : answers) {
                        answerService.saveAnswer(answer);
                    }
                }
            }
        } catch (IOException io) {
            System.out.println(io + ": reading data from file failed");
        }
    }

    public void importQuestionsFromFile(String filepath) {
        try {
            Collection<Question> questions = objectMapper.readValue(new File(filepath), new TypeReference<Collection<Question>>(){});
            for (Question question : questions) {
                questionService.saveQuestion(question);
                Collection<Answer> answers = question.getAnswers();
                for (Answer answer : answers) {
                    answerService.saveAnswer(answer);
                }
            }
        } catch (IOException io) {
            System.out.println(io + ": reading data from file failed");
        }
    }

    public void importAnswersFromFile(String filepath) {
        try {
            Collection<Answer> answers = objectMapper.readValue(new File(filepath), new TypeReference<Collection<Answer>>(){});
            for (Answer answer : answers) {
                answerService.saveAnswer(answer);
            }
        } catch (IOException io) {
            System.out.println(io + ": reading data from file failed");
        }
    }

    public void exportToFile(String filepath) {
        Iterable<User> users = userService.getAllUsers();
        try {
            for (User user : users) {
                objectMapper.writeValue(new File(filepath), user);
            }
        } catch (IOException io) {
            System.out.println(io + ": writing data to file failed");
        }
    }

    public void exportSearchResult(List<Question> questions, String filepath) {
        try {
            for (Question question : questions) {
                objectMapper.writeValue(new File(filepath), question);
            }
        } catch (IOException io) {
            System.out.println(io + ": writing data to file failed");
        }
    }

    public JSONdata(ApplicationContext appContext) {
        userService = appContext.getBean(UserService.class);
        questionService = appContext.getBean(QuestionService.class);
        answerService = appContext.getBean(AnswerService.class);
    }
}