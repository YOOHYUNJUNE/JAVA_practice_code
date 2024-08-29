package com.kosta.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UploadController {
	
	// 이미지 업로드
	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
		
		String uploadDir = "C:\\upload\\temp\\";
		String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

		try {
			// 저장
			Path filePath = Paths.get(uploadDir + fileName);
			Files.copy(file.getInputStream(), filePath);
			return new ResponseEntity<>("업로드 성공 : " + fileName, HttpStatus.OK);
			
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	

	// 이미지 보기
	@GetMapping("/display")
	public ResponseEntity<Resource> display(@RequestParam("filename") String filename) {
		
		String path = "C:\\upload\\temp\\";
		Resource resource = new FileSystemResource(path + filename);
		
		if (!resource.exists())
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		
		HttpHeaders header = new HttpHeaders();
		Path filePath = null;
		
		try {
			// 파일 저장
			filePath = Paths.get(path + filename);
			// 파일 확장자명
			header.add("Content-Type", Files.probeContentType(filePath));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
		
	}
	
	
	
	
}
