package com.kosta.example;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JUnitPractice2 {

	@DisplayName("문제2")
	@Test
	public void practice() {
		int num1 = 15;
		int num2 = 0;
		int num3 = -5;
		
		// 1. num1이 양수인지 확인
		assertThat(num1).isPositive();
		
		// 2. num2가 0인지 확인
		assertThat(num2).isZero();
		
		// 3. num3가 음수인지 확인
		assertThat(num3).isNegative();
		
		// 4. num1은 num2보다 큰 값인지 확인
		assertThat(num1).isGreaterThan(num2);
		
		// 5. num3은 num2보다 작은 값인지 확인
		assertThat(num3).isLessThan(num2);
		
		
	}
	
	
	
	
}
