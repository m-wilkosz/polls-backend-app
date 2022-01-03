package com.proglab.polls;

import java.io.IOException;
import com.proglab.polls.json.JSONdata;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableJpaRepositories("com.proglab.polls.repositories")
public class PollsApplication {
	public static void main(String[] args) throws IOException {
		ApplicationContext appContext = SpringApplication.run(PollsApplication.class, args);
		JSONdata json = new JSONdata(appContext);
	}
}