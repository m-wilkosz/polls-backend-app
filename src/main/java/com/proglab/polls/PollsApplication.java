package com.proglab.polls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.proglab.polls.repositories")
public class PollsApplication {
	public static void main(String[] args) {
		SpringApplication.run(PollsApplication.class, args);
	}
}