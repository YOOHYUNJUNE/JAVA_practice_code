package com.coffee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.coffee.dto.CoffeeDTO;

@Mapper // MyBatis mapper 인터페이스임을 선언
public interface CoffeeMapper {

	public List<CoffeeDTO> selectCoffeeList() throws Exception;
	
}
