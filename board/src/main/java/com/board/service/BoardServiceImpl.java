package com.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.dto.BoardDTO;
import com.board.mapper.BoardMapper;

@Service // 비즈니스 로직을 처리하는 서비스 클래스임을 나타냄

public class BoardServiceImpl implements BoardService {

	@Autowired // Mapper 자동 주입
	private BoardMapper boardMapper;
	
	@Override
	public List<BoardDTO> selectBoardList() throws Exception {
		// 비즈니스 로직(리스트 가져오기)
		return boardMapper.selectBoardList();
	
	}

	@Override
	public void insertBoard(BoardDTO boardDTO) throws Exception {
		boardMapper.insertBoard(boardDTO);
		
	}

	@Override
	public BoardDTO selectBoardById(int id) throws Exception {
		boardMapper.updateHit(id);
		return boardMapper.selectBoardById(id);
	}

	@Override
	public void updateBoard(BoardDTO boardDTO) throws Exception {
		boardMapper.updateBoard(boardDTO);
	}

	@Override
	public void deleteBoard(int id) throws Exception {
		boardMapper.deleteBoardById(id);
	}
	
	
	
	
	
	
	
}
