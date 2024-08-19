package com.kosta.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kosta.dto.Community;
import com.kosta.dto.CommunityFile;

@Mapper
public interface CommunityMapper {

	void save(Community community) throws Exception;

	void insertFiles(List<CommunityFile> fileList) throws Exception;

	List<Community> findAll() throws Exception;

}
