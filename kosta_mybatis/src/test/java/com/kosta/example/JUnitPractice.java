package com.kosta.example;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JUnitPractice {

	@DisplayName("문제1")
	@Test
	public void practice() {
		String name1 = "이제훈";
		String name2 = "이제훈";
		String name3 = "이재훈";
		
		// 1. 모든 변수가 NULL인지 아닌지
		assertThat(name1).isNotNull();
		assertThat(name2).isNotNull();
		assertThat(name3).isNotNull();
		
		// 2. name1과 name2가 같은지
		assertThat(name1).isEqualTo(name2);
		
		// 3. name1과 name3이 다른지
		assertThat(name1).isNotEqualTo(name3);
		
		
	}
	
	
	
	
}
