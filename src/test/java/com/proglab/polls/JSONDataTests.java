package com.proglab.polls;

import java.nio.file.Paths;
import com.proglab.polls.entities.Answer;
import com.proglab.polls.services.UserService;
import com.proglab.polls.services.QuestionService;
import com.proglab.polls.services.AnswerService;
import com.proglab.polls.json.JSONdata;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JSONDataTests {
    ApplicationContext appContext = SpringApplication.run(PollsApplication.class);

    JSONdata json = new JSONdata(appContext);

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    String filepath = Paths.get("").toAbsolutePath().toString();

    @Test
    public void importUsersFromFileTest() {
        json.importUsersFromFile(filepath + "/src/test/java/com/proglab/polls/fileU.json");
        assertEquals("mw98@gmail.com", userService.getById(1).get().getEmailAddress());
    }

    @Test
    public void importQuestionsFromFileTest() {
        json.importQuestionsFromFile(filepath + "/src/test/java/com/proglab/polls/fileQ.json");
        assertEquals("where are you from?", questionService.getById(52).get().getText());
    }

    @Test
    public void importAnswersFromFileTest() {
        json.importAnswersFromFile(filepath + "/src/test/java/com/proglab/polls/fileA.json");
        Answer answer = (Answer)answerService.getByTextContaining("5").toArray()[0];
        assertEquals("5", answer.getText());
    }
}