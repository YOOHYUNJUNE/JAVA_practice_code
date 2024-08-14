package com.kosta.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.dto.CoffeeDTO;
import com.kosta.dto.FileDTO;
import com.kosta.mapper.CoffeeMapper;

@Service
public class CoffeeServiceImpl implements CoffeeService {

	@Autowired
	private CoffeeMapper cm;
	
	
	@Override
	public List<CoffeeDTO> getAllCoffeePosts() throws Exception {
		return cm.selectAllCoffee();
	}

	
	@Override
	public void addCoffeePost(CoffeeDTO coffeeDTO, List<MultipartFile> files) throws Exception {
		cm.insertCoffee(coffeeDTO);
		int coffeeId = coffeeDTO.getId();
		// 파일 저장		
		// 첨부파일이 있으면 저장
		if (files != null && !files.isEmpty()) {
			List<FileDTO> fileList = new ArrayList<>();
			for (MultipartFile file : files) {
				if(!file.isEmpty()) {
					// 원본 파일명
					String originFileName = file.getOriginalFilename();
					// 새 파일명
					String newFileName = UUID.randomUUID().toString() + "_" + originFileName;
					String storedFilePath = "C:\\Users\\WD\\coffee_file\\" + newFileName;
					// 파일 크기
					long fileSize = file.getSize();
					
					FileDTO fileDTO = new FileDTO();
					fileDTO.setCoffeeId(coffeeId);
					fileDTO.setOriginFileName(originFileName);
					fileDTO.setStoredFilePath(storedFilePath);
					fileDTO.setFileSize(fileSize);
					fileList.add(fileDTO);
					
					File dest = new File(storedFilePath);
					file.transferTo(dest);
					
				}
			}
			// file 테이블에 추가
			if (!fileList.isEmpty()) cm.insertFile(fileList);
			
		}
	}

	
	@Override
	public void deleteCoffeePost(int id) throws Exception {
		cm.deleteCoffee(id);
	}

	
	@Override
	public CoffeeDTO getCoffeePost(int id) throws Exception {
		CoffeeDTO coffeeDTO = cm.selectCoffeeById(id);
		// 조회수 기능은 만들지 않음
		List<FileDTO> fileList = cm.selectAllFileByCoffeeId(id);
		coffeeDTO.setFileList(fileList);
		
		return coffeeDTO;
	}

	
	@Override
	public void modifyCoffeePost(CoffeeDTO coffeeDTO, List<MultipartFile> files) throws Exception {
		cm.updateCoffee(coffeeDTO);
		
		// fileTbl에 저장(수정)
		List<FileDTO> fileList = new ArrayList<>();
		
		// 기존 파일 삭제(덮어쓰기)
		cm.deleteFileByCoffeeId(coffeeDTO.getId());
		
		cm.insertFile(fileList);
	}

	
	
	
}
