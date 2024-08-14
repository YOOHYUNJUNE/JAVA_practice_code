package com.kosta.dto;

import lombok.Data;

@Data
public class FileDTO {

	private int id, coffeeId;
	private String originFileName, storedFilePath;
	private long fileSize;
	
}
