package com.kosta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RestBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestBlogApplication.class, args);
	}

}
