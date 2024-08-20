package com.kosta.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kosta.dto.Community;
import com.kosta.dto.CommunityFile;

public interface CommunityService {

	public void add(Community community, int id, List<MultipartFile> files) throws Exception;

	public List<Community> getAllCommunity() throws Exception;

	public Community getCommunityById(int id) throws Exception;

	public CommunityFile getCommunityFileById(int id) throws Exception;

	public void delete(int id) throws Exception;


}
