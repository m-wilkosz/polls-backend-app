package com.proglab.polls;

import java.util.List;
import java.util.Scanner;
import com.proglab.polls.entities.Question;
import com.proglab.polls.services.QuestionService;
import com.proglab.polls.json.JSONdata;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableJpaRepositories("com.proglab.polls.repositories")
public class PollsApplication {
	public static void main(String[] args) {
		ApplicationContext appContext = SpringApplication.run(PollsApplication.class, args);
		JSONdata json = new JSONdata(appContext);
		QuestionService questionService = appContext.getBean(QuestionService.class);
		List<Question> searchedQuestions;
		String c = "n", filepath, keyword;
		int choice;
		Scanner scan = new Scanner(System.in);
		while (c.equals("n")) {
			System.out.println("=============== menu ===============");
			System.out.println("1. search questions by keyword");
			System.out.println("2. import users from json file");
			System.out.println("3. import questions from json file");
			System.out.println("4. import answers from json file");
			System.out.println("5. export whole data to json file");
			System.out.println("====================================");
			System.out.println("your choice: ");
			choice = scan.nextInt();
			scan.nextLine();
			if (choice == 1) {
				System.out.println("provide keyword: ");
				keyword = scan.nextLine();
				searchedQuestions = questionService.getByTextContaining(keyword);
				if (!searchedQuestions.isEmpty()) {
					for (Question question : searchedQuestions) {
						System.out.println("question id: " + question.getId());
						System.out.println("user id: " + question.getUser().getId());
						System.out.println("text: " + question.getText());
						System.out.println();
					}
					System.out.println("export query result to json file? (y/n)");
					c = scan.nextLine();
					if (c.equals("y")) {
						System.out.println("provide filepath: ");
						filepath = scan.nextLine();
						json.exportSearchResult(searchedQuestions, filepath);
					}
				} else {
					System.out.println("no question containing keyword \"" + keyword + "\"");
				}
			} else {
				System.out.println("provide filepath: ");
				filepath = scan.nextLine();
				switch (choice) {
					case 2: json.importUsersFromFile(filepath); break;
					case 3: json.importQuestionsFromFile(filepath); break;
					case 4: json.importAnswersFromFile(filepath); break;
					case 5: json.exportToFile(filepath); break;
					default: System.out.println("wrong option number");
				}
			}
			System.out.println("do you want to exit application? (y/n)");
			c = scan.nextLine();
			System.out.print("\033[H\033[2J");
			System.out.flush();
		}
	}
}