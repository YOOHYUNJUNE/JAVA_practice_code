package com.kosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosta.entity.Participate;

@Repository
public interface ParticipateRepository extends JpaRepository<Participate, Long>{

	List<Participate> findByMemberId(Long userId);

	Participate findByStudyGroupIdAndMemberId(Long studyGroupId, Long memberId);

	List<Participate> findByStudyGroupId(Long studyGroupId);

}
