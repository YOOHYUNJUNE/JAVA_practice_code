package com.kosta.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.dto.Community;
import com.kosta.dto.CommunityFile;
import com.kosta.dto.User;
import com.kosta.mapper.CommunityMapper;
import com.kosta.mapper.UserMapper;

@Service
public class ICommunityService implements CommunityService {

	@Autowired
	private CommunityMapper cm;
	@Autowired
	private UserMapper um;
	
	
//	// 게시글 전체 목록
//	@Override
//	public List<Community> getAllCommunity() throws Exception {
//		return cm.findAll();
//	}
//	
	
	// 게시글 전체 목록(섬네일)
	@Override
	public List<Community> getAllCommunity() throws Exception {
		List<Community> communityList = cm.findAll();
		for (Community community : communityList) {
			List<CommunityFile> fileList = cm.findFileByCommunityId(community.getId());
			community.setFileList(fileList);
		}
		return communityList;
		
	}
	
	
	// 게시글 상세보기
	@Override
	public Community getCommunityById(int id) throws Exception {
		Community community = cm.findById(id);
		List<CommunityFile> fileList = cm.findFileByCommunityId(id);
		community.setFileList(fileList);
		
		return community;
	}
	
	

	
	// 게시글 작성
	// Community 안에 creatorId를 매칭해야 함
	@Override
	public void add(Community community, int id, List<MultipartFile> files) throws Exception {
		// 작성자 ID값을 통해, 커뮤니티 모델에 작성자 Creator 세팅
		User user = um.findMyId(id);
		community.setCreator(user);
		
		// 게시글 저장
		cm.save(community);
		
		// 커뮤ID
		int communityId = community.getId();
		
		// 이미지 저장 (커뮤티니 ID, 원본 파일명, 새 파일명, 파일크기)
		if (!files.isEmpty()) {
			List<CommunityFile> fileList = new ArrayList<>();
			
			for (MultipartFile file : files) {
				if (!file.isEmpty()) {
					// 원본 파일명, 새 파일명 가져오기
					String originFileName = file.getOriginalFilename();
					String newFileName = UUID.randomUUID().toString() + "_" + originFileName;
					long fileSize = file.getSize();
					
					// 가져온 내용으로 인스턴스 생성
					CommunityFile cf = new CommunityFile();
					cf.setCommunityId(communityId);
					cf.setOriginFileName(originFileName);
					cf.setStoredFilePath(newFileName);
					cf.setFileSize(fileSize);
					
					// cf를 fileList 추가
					fileList.add(cf);
					
					// PC에 저장
					File dest = new File("C:\\Users\\WD\\images\\" + newFileName);
					file.transferTo(dest);
				}
			}
			// fileList가 비어있으면 DB에 추가
			if(!fileList.isEmpty()) {
				cm.insertFiles(fileList);
			}
			
		}
		
	}


	// 이미지 다운로드
	@Override
	public CommunityFile getCommunityFileById(int id) throws Exception {
		return cm.findFileById(id);
	}

	
	// 게시글 삭제
	@Override
	public void delete(int id) throws Exception {
		// community_file_tbl 에서, 게시물에 있는 file먼저 삭제해야함
		cm.deleteFilesByCommunityId(id);
		cm.delete(id); // community_tbl에서 삭제
	}










	





}
