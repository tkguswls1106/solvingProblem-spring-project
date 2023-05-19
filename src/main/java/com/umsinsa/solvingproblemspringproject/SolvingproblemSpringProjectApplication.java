package com.umsinsa.solvingproblemspringproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SolvingproblemSpringProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolvingproblemSpringProjectApplication.class, args);
	}

}
