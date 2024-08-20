package com.kosta.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JUnitCycleTest {
	
	@BeforeAll
	public static void beforeAll() {
		System.out.println("@BeforeAll : 전체 테스트 시작전 1회 실행");
	}
	
	
	@BeforeEach
	public void beforeEach() {
		System.out.println("@BeforeEach : 테스트 케이스 시작하기 전마다 실행");
	}
	
	
	@AfterAll
	public static void afterAll() {
		System.out.println("@AfterAll : 전체 테스트 종료 직전 1회 실행");
	}
	
	
	@AfterEach
	public void afterEach() {
		System.out.println("@AfterEach : 테스트 케이스 종료하기 전마다 실행");
	}
	
	
	@Test
	public void test1 () {
		System.out.println("테스트 1");
	}	
	
	@Test
	public void test2 () {
		System.out.println("테스트 2");
	}	
	
	@Test
	public void test3 () {
		System.out.println("테스트 3");
	}	
	
	@Test
	public void test4 () {
		System.out.println("테스트 4");
	}	
	
	@Test
	public void test5 () {
		System.out.println("테스트 5");
	}
	
	
	
}
