package com.proglab.polls;

import com.proglab.polls.entities.User;
import com.proglab.polls.entities.Question;
import com.proglab.polls.services.UserService;
import com.proglab.polls.services.QuestionService;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.core.JsonProcessingException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTests {
	@Autowired
	private UserService userService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private MockMvc mvc;

	DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

	@Test
	@Order(1)
	public void postUserTest() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders
						.post("/api/user")
						.content(asJSON(new User("user999", "janusz_k@gmail.com",
								formatter.parseDateTime("2022-01-01 12:00:00"),
								formatter.parseDateTime("2022-01-01 12:00:00"))))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andReturn();
		assertEquals("{\"id\":1,\"username\":\"user999\",\"emailAddress\":\"janusz_k@gmail.com\"," +
				"\"joiningDate\":\"2022-01-01 11:00:00\",\"lastActive\":\"2022-01-01 11:00:00\"," +
				"\"questions\":[]}", result.getResponse().getContentAsString());
	}

	@Test
	@Order(2)
	public void getUsersTest() throws Exception {
		mvc.perform(get("/api/users")
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
						.andExpect(jsonPath("$[0].username").value("user999"))
						.andExpect(jsonPath("$[0].emailAddress").value("janusz_k@gmail.com"))
						.andExpect(jsonPath("$[0].joiningDate").value("2022-01-01 11:00:00"))
						.andExpect(jsonPath("$[0].lastActive").value("2022-01-01 11:00:00"));
	}

	@Test
	@Order(3)
	public void deleteUsersTest() throws Exception {
		mvc.perform(delete("/api/users")
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
						.andExpect(jsonPath("$[0].username").value("user999"))
						.andExpect(jsonPath("$[0].emailAddress").value("janusz_k@gmail.com"))
						.andExpect(jsonPath("$[0].joiningDate").value("2022-01-01 11:00:00"))
						.andExpect(jsonPath("$[0].lastActive").value("2022-01-01 11:00:00"));
	}

	@Test
	@Order(4)
	public void putUserTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders
						.put("/api/user/{id}", 1)
						.content(asJSON(new User("user999", "janusz_kowalski@gmail.com",
								formatter.parseDateTime("2022-01-01 12:00:00"),
								formatter.parseDateTime("2022-01-01 12:00:00"))))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isCreated());
		assertEquals("janusz_kowalski@gmail.com", userService.getById(1).get().getEmailAddress());
	}

	@Test
	@Order(5)
	public void getUserByUsernameTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders
						.get("/api/user/{username}", "user999"))
						.andExpect(status().isOk())
						.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
						.andExpect(jsonPath("$.['username']").value("user999"))
						.andExpect(jsonPath("$.['emailAddress']").value("janusz_kowalski@gmail.com"))
						.andExpect(jsonPath("$.['joiningDate']").value("2022-01-01 11:00:00"))
						.andExpect(jsonPath("$.['lastActive']").value("2022-01-01 11:00:00"));
	}

	@Test
	@Order(6)
	public void getNumOfQuestionsByIdTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders
						.get("/api/user/{id}/number-of-questions", 1))
						.andExpect(status().isOk())
						.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
						.andExpect(content().string("0"));
	}

	@Test
	@Order(7)
	public void getQuestionsByIdTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders
						.get("/api/user/{id}/questions", 1))
						.andExpect(status().isOk())
						.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
						.andExpect(content().string("[]"));
	}

	@Test
	@Order(8)
	public void getActiveAfterTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders
						.get("/api/users/active-after/{d}-{m}-{y}", 31, 12, 2021))
						.andExpect(status().isOk())
						.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
						.andExpect(jsonPath("$[0].username").value("user999"))
						.andExpect(jsonPath("$[0].emailAddress").value("janusz_kowalski@gmail.com"))
						.andExpect(jsonPath("$[0].joiningDate").value("2022-01-01 11:00:00"))
						.andExpect(jsonPath("$[0].lastActive").value("2022-01-01 11:00:00"));
	}

	@Test
	@Order(9)
	public void getNumberOfJoinedAfterTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders
						.get("/api/users/number-of-joined-after/{d}-{m}-{y}", 31, 12, 2021))
						.andExpect(status().isOk())
						.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
						.andExpect(content().string("1"));
	}

	@Test
	@Order(10)
	public void getMostActiveTest() throws Exception {
		Question question = new Question("where are you from?", formatter.parseDateTime("2022-01-01 12:00:00"));
		question.setUser(userService.getById(1).get());
		questionService.saveQuestion(question);
		mvc.perform(MockMvcRequestBuilders
						.get("/api/users/most-active"))
						.andExpect(status().isOk())
						.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
						.andExpect(jsonPath("$.['username']").value("user999"))
						.andExpect(jsonPath("$.['emailAddress']").value("janusz_kowalski@gmail.com"))
						.andExpect(jsonPath("$.['joiningDate']").value("2022-01-01 11:00:00"))
						.andExpect(jsonPath("$.['lastActive']").value("2022-01-01 11:00:00"));
	}

	@Test
	@Order(11)
	public void deleteUserTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders
						.delete("/api/user/{id}", 1))
						.andExpect(redirectedUrl("/api/users"));
	}

	public static String asJSON(User user) throws JsonProcessingException {
		return new ObjectMapper().registerModule(new JodaModule()).writeValueAsString(user);
	}
}