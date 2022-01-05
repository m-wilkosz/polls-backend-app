package com.proglab.polls;

import java.util.TimeZone;
import com.proglab.polls.entities.User;
import com.proglab.polls.services.UserService;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class PollsApplicationTests {
	@Autowired
	private UserService userService;

	@Autowired
	private MockMvc mvc;

	@Before
	public void setUp() {
		DateTimeZone.setDefault(DateTimeZone.forTimeZone(TimeZone.getTimeZone("CET")));
	}

	DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

	// UserController tests
	@Test
	public void getUsersTest() throws Exception {
		createUser();
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
	public void deleteUserTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.delete("/api/user/{id}", 1))
				.andExpect(redirectedUrl("/api/users"));
	}

	@Test
	public void postUserTest() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders
				.post("/api/user")
						.content(asJsonString(new User("user999", "janusz_k@gmail.com",
								formatter.parseDateTime("2022-01-01 12:00:00"), formatter.parseDateTime("2022-01-01 12:00:00"))))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
      			.andExpect(status().isOk())
				.andReturn();
		String content = result.getResponse().getContentAsString();
		assertEquals("{\"id\":1,\"username\":\"user999\",\"emailAddress\":\"janusz_k@gmail.com\"," +
				"\"joiningDate\":\"2022-01-01 11:00:00\",\"lastActive\":\"2022-01-01 11:00:00\"," +
				"\"questions\":[]}", content);
	}

	public void createUser() {
		userService.saveUser(new User("user999", "janusz_k@gmail.com",
				formatter.parseDateTime("2022-01-01 12:00:00"), formatter.parseDateTime("2022-01-01 12:00:00")));
	}

	public static String asJsonString(User user) {
		try {
			return new ObjectMapper().registerModule(new JodaModule()).writeValueAsString(user);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}