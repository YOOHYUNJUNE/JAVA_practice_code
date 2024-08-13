package com.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.board.dto.FileDTO;

@Mapper
public interface FileMapper {
	void insertFile(List<FileDTO> fileList) throws Exception;

	List<FileDTO> selectFileListByBoardId(int boardId) throws Exception;
}
