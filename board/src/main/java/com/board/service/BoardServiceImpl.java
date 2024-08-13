package com.board.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.board.dto.BoardDTO;
import com.board.dto.FileDTO;
import com.board.mapper.BoardMapper;
import com.board.mapper.FileMapper;

@Service // 비즈니스 로직을 처리하는 서비스 클래스임을 나타냄
@Transactional // 오류시에는 조회수 증가 등 적용되지 않게 함
public class BoardServiceImpl implements BoardService {

	@Autowired // Mapper 자동 주입
	private BoardMapper boardMapper;
	@Autowired
	private FileMapper fileMapper;
	
	@Override
	public List<BoardDTO> selectBoardList() throws Exception {
		// 비즈니스 로직(리스트 가져오기)
		return boardMapper.selectBoardList();
	
	}

	@Override
	public void insertBoard(BoardDTO boardDTO, List<MultipartFile> files) throws Exception {
//		System.out.println(boardDTO);
		boardMapper.insertBoard(boardDTO); // useGeneratedKeys="true" keyProperty="id"로 id 갱신
//		System.out.println(boardDTO.getId());
		int boardId = boardDTO.getId();
		
		// 첨부파일이 있으면.
		if (files != null && !files.isEmpty()) {
			// 파일이 여러개일 경우를 대비해 List 생성
			List<FileDTO> fileList = new ArrayList<>();
			
			// 파일을 하나씩 가져옴
			for(MultipartFile file : files) {
				// 파일 존재시
				if (!file.isEmpty()) {
					// 원본 파일명
					String originFileName = file.getOriginalFilename();
					// 새로운 파일명
					String storedFileName = UUID.randomUUID().toString() + "_" +  originFileName; // 랜덤값 + 원본명
					// 파일 저장경로 + 새로운 파일명
					String storedFilePath = "C:\\Users\\WD\\board_file\\" + storedFileName;
					// 파일 크기
					long fileSize = file.getSize();
					
					// fileDTO 생성하여 List에 추가
					FileDTO fileDTO = new FileDTO();
					fileDTO.setBoardId(boardId);
					fileDTO.setFileSize(fileSize);
					fileDTO.setOriginFileName(originFileName);
					fileDTO.setStoredFilePath(storedFilePath);
					fileList.add(fileDTO);
					
					// 파일 저장
					try {						
						File dest = new File(storedFilePath); // 주소만 있는 데이터
						file.transferTo(dest);
					} catch (IOException e) {
						throw new Exception("파일 업로드 중 오류가 발생했습니다.");
					}
					
				}
			} // for문 종료
			// DB에 file 데이터 보내기
			if (!fileList.isEmpty()) {
				fileMapper.insertFile(fileList);
			}
		}
		
	}

	// 조회수 증가
	@Override
	public BoardDTO selectBoardById(int id) throws Exception {
		BoardDTO boardDTO = boardMapper.selectBoardById(id);
		// 게시물에 들어가면 첨부된 파일 보여주기
		List<FileDTO> fileList = fileMapper.selectFileListByBoardId(id);
		boardDTO.setFileList(fileList); 
		boardMapper.updateHit(id);
		return boardDTO;
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
