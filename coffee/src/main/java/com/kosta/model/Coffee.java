package com.coffee.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Coffee {
	
	private int id, price, caffeine, sugar;
	private String name, img, detail;
	
	// id 외 객체 생성
	public Coffee(String name, String img, int price, int caffeine, int sugar, String detail) {
		this.name = name;
		this.img = img;
		this.price = price;
		this.caffeine = caffeine;
		this.sugar = sugar;
		this.detail = detail;
	}

	// DAOImpl > getAll에서 롬복 인식을 못해서?? 7개 매개변수 다 생성
	public Coffee(int id, String name, String img, int price, int caffeine, int sugar, String detail) {
		this.id = id;
		this.name = name;
		this.img = img;
		this.price = price;
		this.caffeine = caffeine;
		this.sugar = sugar;
		this.detail = detail;
	}
}


//# coffee_db 생성
//drop database if exists coffee_db;
//
//create database coffee_db default character set = 'utf8mb4' collate utf8mb4_0900_ai_ci;
//
//use coffee_db;
//
//# 테이블 생성
//create table coffee (
//	id int primary key auto_increment,
//	name varchar(255) not null,
//	img varchar(255) not null,
//	price int not null,
//	caffeine int not null default 0,
//	sugar int not null default 0,
//	detail varchar(255) not null 
//);
//
//select * from coffee;