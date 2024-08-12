package com.board.service;

import java.util.List;

import com.board.dto.BoardDTO;

public interface BoardService {
	List<BoardDTO> selectBoardList() throws Exception;

	void insertBoard(BoardDTO boardDTO) throws Exception;

	BoardDTO selectBoardById(int id) throws Exception;

	void updateBoard(BoardDTO boardDTO) throws Exception;

	void deleteBoard(int id) throws Exception; 
	
	
	
}
