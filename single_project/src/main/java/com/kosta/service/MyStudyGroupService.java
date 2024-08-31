package com.kosta.service;

import java.util.List;

import com.kosta.domain.StudyGroupDTO;

public interface MyStudyGroupService {

	void createStudyGroup(Long userId, StudyGroupDTO sgDTO) throws Exception;

	void joinStudyGroup(Long studyGroupId, Long userId) throws Exception;

	List<StudyGroupDTO> findStudyGroupByMemberId(Long userId);

	void quitStudyGroup(Long studyGroupId, Long id);

	StudyGroupDTO findStudyGroupById(Long id);
}
