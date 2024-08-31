package com.kosta.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kosta.domain.StudyGroupDTO;
import com.kosta.entity.Participate;
import com.kosta.entity.StudyGroup;
import com.kosta.entity.User;
import com.kosta.repository.ParticipateRepository;
import com.kosta.repository.StudyGroupRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudyGroupServiceImpl implements StudyGroupService {

	private final StudyGroupRepository studyGroupRepository;
	private final ParticipateRepository participateRepository;
	
	@Override
	public StudyGroupDTO findStudyGroupById(Long id, User user) {
		StudyGroup sg = studyGroupRepository.findById(id).get();
		Long userId = user != null ? user.getId() : null;
		List<Participate> plist = participateRepository.findByMemberId(userId);
		
		StudyGroupDTO studyGroupDTO = StudyGroupDTO.setStudyGroupDTO(sg);
		studyGroupDTO.setShowManage(sg.getLeader().getId().equals(userId));
		studyGroupDTO.setJoined(plist.stream().anyMatch(p-> p.getStudyGroup().getId().equals(sg.getId())));
		return studyGroupDTO;
	}
	
	@Override
	public List<StudyGroupDTO> findAll(Integer sort) {
		List<StudyGroup> list  = null; 
		if (sort == null) {
			list = studyGroupRepository.findAll();
		} else if (sort == 1) {
			// 그룹명
			list = studyGroupRepository.findAllByOrderByName();
		} else if (sort == 2) {
			// 스터디 리더
			list = studyGroupRepository.findAllByOrderByLeaderName();
		} else if (sort == 3) {
			// 시작일
			list = studyGroupRepository.findAllByOrderByStartedAt();
		} else if (sort == 4) {
			// 종료일
			list = studyGroupRepository.findAllByOrderByFinishedAt();
		} else if (sort == 5) {
			// 기간
			list = studyGroupRepository.findAllByOrderByPeriod();
		}
		
		return list.stream().map(sg -> 	
			StudyGroupDTO.setStudyGroupDTO(sg)
		).collect(Collectors.toList());
	}
	
	@Override
	public List<StudyGroupDTO> findByLeaderId(Long leaderId) {
		List<StudyGroup> list = studyGroupRepository.findByLeaderId(leaderId);
		return list.stream().map(sg -> 			
			StudyGroupDTO.setStudyGroupDTO(sg)
		).collect(Collectors.toList());
	}

	@Override
	public void modifyStudyGroup(StudyGroupDTO studyGroupDTO, Long userId) throws Exception {
		
		LocalDate startDate = LocalDate.parse(studyGroupDTO.getStartedAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate finishDate = LocalDate.parse(studyGroupDTO.getFinishedAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		if (finishDate.isBefore(startDate)) {
			throw new Exception("시작일보다 종료일이 이전일 수 없습니다.");
		}
		
		StudyGroup studyGroup = studyGroupRepository.findById(studyGroupDTO.getId()).get();
		if (studyGroup.getLeader().getId().equals(userId)) {
			studyGroup.setName(studyGroupDTO.getName());
			studyGroup.setDescription(studyGroupDTO.getDescription());
			studyGroup.setStartedAt(startDate.atStartOfDay());
			studyGroup.setFinishedAt(finishDate.atStartOfDay());
			studyGroupRepository.save(studyGroup);
		}
	}
	
	@Override
	public void deleteStudyGroup(Long studyGroupId, Long userId) {
		StudyGroup studyGroup = studyGroupRepository.findById(studyGroupId).get();
		if (studyGroup.getLeader().getId().equals(userId)) {
			studyGroupRepository.delete(studyGroup);
		}
	}

}
