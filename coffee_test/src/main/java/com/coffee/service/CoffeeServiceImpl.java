package com.coffee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.coffee.dto.CoffeeDTO;
import com.coffee.mapper.CoffeeMapper;

public class CoffeeServiceImpl implements CoffeeService {

	@Autowired // Mapper 자동 주입
	private CoffeeMapper coffeeMapper;
	
	// 리스트 가져오기
	@Override
	public List<CoffeeDTO> selectCoffeeList() throws Exception {
		return coffeeMapper.selectCoffeeList();
	}

}
