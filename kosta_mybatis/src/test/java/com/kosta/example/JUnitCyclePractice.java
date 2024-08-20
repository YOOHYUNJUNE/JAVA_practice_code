package com.kosta.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JUnitCyclePractice {

		// 각 테스트 시작 전 Hello! 출력 // @BeforeEach
		// 모든 테스트 후 Bye! 출력 // AfterAll
		
	@BeforeEach
	public void beforeEach() {
		System.out.println("Hello!");
	}
	
	@AfterAll
	public static void afterAll() {
		System.out.println("Bye!");
	}
	
	
	@Test
	public void test1() {
		System.out.println("첫번째 테스트");
	}
	
	@Test
	public void test2() {
		System.out.println("두번째 테스트");
	}
		
		
		
		
		
}
	
	
	
	

