package com.kosta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JpaTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaTestApplication.class, args);
	}

}
