package com.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.board.dto.BoardDTO;
import com.board.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j // 로그
@Controller // 컨트롤러를 지정하는 애너테이션
public class BoardController {
	@Autowired // 서비스 Bean 자동 주입
	private BoardService bs;
	
	@RequestMapping("/board/list") // 요청에 맞는 주소 지정
	public ModelAndView boardList() throws Exception {
		
		String data = "로그 출력 연습";
		log.trace("trace: {}", data);
		log.debug("debug: {}", data);
		log.info("info: {}", data);
		log.warn("warn: {}", data);
		log.error("error: {}", data);
		
		
		// src/main/resources/templates/board/list.html로 화면 지정
		ModelAndView mv = new ModelAndView("board/list");
		// 비즈니스 로직 수행
		List<BoardDTO> boardList = bs.selectBoardList();
		mv.addObject("list", boardList);
		return mv;
	}

	
	
}
