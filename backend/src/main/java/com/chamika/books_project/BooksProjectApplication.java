package com.chamika.books_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class BooksProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooksProjectApplication.class, args);
	}

}
