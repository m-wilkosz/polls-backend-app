package com.proglab.polls;

import java.io.IOException;
import com.proglab.polls.json.JSONdata;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.proglab.polls.repositories")
public class PollsApplication {
	public static void main(String[] args) throws IOException {
		SpringApplication.run(PollsApplication.class, args);
		JSONdata json = new JSONdata();
		json.importFile("/home/wilkosz/Desktop/file.json");
	}
}