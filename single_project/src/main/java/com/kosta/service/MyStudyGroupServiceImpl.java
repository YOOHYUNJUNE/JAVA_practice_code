package com.kosta.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.kosta.domain.StudyGroupDTO;
import com.kosta.domain.UserDTO;
import com.kosta.domain.UserRole;
import com.kosta.entity.Participate;
import com.kosta.entity.StudyGroup;
import com.kosta.entity.User;
import com.kosta.repository.ParticipateRepository;
import com.kosta.repository.StudyGroupRepository;
import com.kosta.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyStudyGroupServiceImpl implements MyStudyGroupService{

	private final UserRepository userRepository;
	private final StudyGroupRepository studyGroupRepository;
	private final ParticipateRepository participateRepository;
	
	@Override
	public StudyGroupDTO findStudyGroupById(Long id) {
		StudyGroup sg = studyGroupRepository.findById(id).get();
		List<Participate> pList = participateRepository.findByStudyGroupId(id);
		StudyGroupDTO studyGroupDTO = StudyGroupDTO.setStudyGroupDTO(sg);
		studyGroupDTO.setMemberList(pList.stream().map(p -> UserDTO.setUserDTO(p.getMember())).collect(Collectors.toList()));
		return studyGroupDTO;
	}
	
	@Override
	@Transactional
	public void createStudyGroup(Long userId, StudyGroupDTO sgDTO) throws Exception {
		User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException());
		
		LocalDate startDate = LocalDate.parse(sgDTO.getStartedAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate finishDate = LocalDate.parse(sgDTO.getFinishedAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		if (finishDate.isBefore(startDate)) {
			throw new Exception("시작일보다 종료일이 이전일 수 없습니다.");
		}
		
		if (user.getRole().equals(UserRole.MEMBER)) {
			user.setRole(UserRole.LEADER);
			user = userRepository.save(user);
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
			updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + UserRole.LEADER));
			Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
			SecurityContextHolder.getContext().setAuthentication(newAuth);
		}
		sgDTO.setLeader(user);
		studyGroupRepository.save(sgDTO.setStudyGroup());
	}

	@Override
	@Transactional
	public void joinStudyGroup(Long studyGroupId, Long userId) throws Exception {
		StudyGroup sg = studyGroupRepository.findById(studyGroupId).get();
		
		LocalDateTime finishedAt = sg.getFinishedAt();
		LocalDateTime now = LocalDateTime.now();
		
		if (finishedAt.isBefore(now)) {
			throw new Exception("이미 종료된 스터디 그룹입니다.");
		}
		
		if (sg.getLeader().getId().equals(userId)) {
			throw new Exception("스터디 그룹 개설자는 멤버가 될 수 없습니다.");
		}
		
		Participate p = new Participate();
		p.setStudyGroup(sg);
		p.setMember(userRepository.findById(userId).get());
		participateRepository.save(p);
	}

	@Override
	public List<StudyGroupDTO> findStudyGroupByMemberId(Long userId) {
		List<Participate> list = participateRepository.findByMemberId(userId);
		return list.stream().map(p -> 			
			StudyGroupDTO.setStudyGroupDTO(p.getStudyGroup())
		).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void quitStudyGroup(Long studyGroupId, Long memberId) {
		Participate p = participateRepository.findByStudyGroupIdAndMemberId(studyGroupId, memberId);
		participateRepository.delete(p);
	}
}
