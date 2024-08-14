package com.kosta.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class CoffeeDTO {
	
	private int id, price, caffeine, sugar;
	private String name, detail;
	private boolean ice;
	private UserDTO creator;
	private LocalDateTime createAt;
	private List<FileDTO> fileList;
	
	
}
