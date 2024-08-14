package com.kosta.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kosta.dto.CoffeeDTO;
import com.kosta.dto.FileDTO;

@Mapper
public interface CoffeeMapper {

	// 커피
	List<CoffeeDTO> selectAllCoffee();

	void insertCoffee(CoffeeDTO coffeeDTO);

	CoffeeDTO selectCoffeeById(int id);

	void updateCoffee(CoffeeDTO coffeeDTO);

	void deleteCoffee(int id);

	// 파일 관련
	
	
	void insertFile(List<FileDTO> fileList);

	void deleteFileByCoffeeId(int id);

	List<FileDTO> selectAllFileByCoffeeId(int id);
	
	
	
}
