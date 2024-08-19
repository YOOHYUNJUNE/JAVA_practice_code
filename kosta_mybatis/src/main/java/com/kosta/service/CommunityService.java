package com.kosta.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kosta.dto.Community;

public interface CommunityService {

	public void add(Community community, int id, List<MultipartFile> files) throws Exception;

	public List<Community> getAllCommunity() throws Exception;

	public Community getCommunityById(int id) throws Exception;


}
