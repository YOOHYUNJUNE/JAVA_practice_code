package com.board.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriUtils;

import com.board.dto.BoardDTO;
import com.board.dto.FileDTO;
import com.board.service.BoardService;

import lombok.extern.slf4j.Slf4j;




@Slf4j // 로그
@Controller // 컨트롤러를 지정하는 애너테이션
@RequestMapping("/board") // 모든 경로에 board를 거치게 함
public class BoardController {
	@Autowired // 서비스 Bean 자동 주입
	private BoardService bs;
	
	@RequestMapping("/list") // 요청에 맞는 주소 지정
	public ModelAndView boardList() throws Exception {
		
//		String data = "로그 출력 연습";
//		log.trace("trace: {}", data);
//		log.debug("debug: {}", data);
//		log.info("info: {}", data);
//		log.warn("warn: {}", data);
//		log.error("error: {}", data);
		
		
		// src/main/resources/templates/board/list.html로 화면 지정
		ModelAndView mv = new ModelAndView("board/list");
		// 비즈니스 로직 수행
		List<BoardDTO> boardList = bs.selectBoardList();
		mv.addObject("list", boardList);
		return mv;
	}

	@GetMapping("/insert")
	public String boardInsertView() throws Exception {
		log.info("글쓰기 페이지로 이동");
		return "board/write";
	}
	

	@PostMapping("/insert")
	public String boardInsert(BoardDTO boardDTO, @RequestParam(value="files", required=false) List<MultipartFile> files) throws Exception {
		// 글쓰기 비즈니스 로직
		bs.insertBoard(boardDTO, files);
		return "redirect:/board/list"; // 글 작성후 board/list로 이동
	}
	
	
	@RequestMapping("/detail")
	public ModelAndView boardDetail(@RequestParam("id") int id) throws Exception {
		ModelAndView mv = new ModelAndView("board/detail");
		BoardDTO boardDTO = bs.selectBoardById(id);
		mv.addObject("board", boardDTO);
		return mv;
	}
	
	
	@PostMapping("/update")
	public String boardUpdate(BoardDTO boardDTO) throws Exception {
		bs.updateBoard(boardDTO);
		return "redirect:/board/list";
	}
	
	
	
	@PostMapping("/delete")
	public String boardDelete(@RequestParam("id") int id) throws Exception {
		bs.deleteBoard(id);
		return "redirect:/board/list";
	}
	
	
	// 파일 다운로드
	@RequestMapping("/download")
	public ResponseEntity<Resource> downloadFile(@RequestParam("id") int id, @RequestParam("boardId") int boardId) throws Exception {
		// 서비스 역할 : Mapper를 통해 id와 boardId가 일치하는 파일DTO를 가져오고, 파일 리소스를 만들어서 반환
		FileDTO fileDTO = bs.selectFileByIds(id, boardId);
		String fileName = fileDTO.getOriginFileName();
		UrlResource resource;
		
		try {
			resource = new UrlResource("file : " + fileDTO.getStoredFilePath());
		} catch (Exception e) {
			throw new Exception("파일 다운로드 에러");
		}
		
		// 서버로부터 파일을 가져와서 다듬기?
		String encodedFileName = UriUtils.encode(fileName, StandardCharsets.UTF_8);
		String contentDispositionValue = "attactment; filename=\""+ encodedFileName +"\"";
		return ResponseEntity
			.ok() // 응답 코드
			.header(HttpHeaders.CONTENT_DISPOSITION, contentDispositionValue) // key, value
			.body(resource);
	}
	
	
	
	// 예외처리
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception e) {
		log.error("예외 발생 : {} ", e.getMessage());
		ModelAndView mv = new ModelAndView("board/error");
		mv.addObject("errorMsg", e.getMessage());
		return mv;
	}
	
	
	
	
	
	
	
}
