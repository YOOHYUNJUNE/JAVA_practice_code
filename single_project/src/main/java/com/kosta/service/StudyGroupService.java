package com.kosta.service;

import java.util.List;

import com.kosta.domain.StudyGroupDTO;
import com.kosta.entity.User;

public interface StudyGroupService {

	List<StudyGroupDTO> findAll(Integer sort);

	List<StudyGroupDTO> findByLeaderId(Long id);

	void deleteStudyGroup(Long studyGroupId, Long id);

	StudyGroupDTO findStudyGroupById(Long id, User user);

	void modifyStudyGroup(StudyGroupDTO studyGroupDTO, Long userId) throws Exception;

}
