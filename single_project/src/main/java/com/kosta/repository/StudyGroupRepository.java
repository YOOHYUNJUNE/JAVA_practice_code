package com.kosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kosta.entity.StudyGroup;

@Repository
public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long>{

	List<StudyGroup> findByLeaderId(Long leaderId);

	@Query("SELECT sg FROM study_group sg LEFT JOIN sg.leader user ORDER BY user.name")
	List<StudyGroup> findAllByOrderByLeaderName();
	
	List<StudyGroup> findAllByOrderByName();

	List<StudyGroup> findAllByOrderByStartedAt();

	List<StudyGroup> findAllByOrderByFinishedAt();

	@Query("SELECT sg, sg.finishedAt - sg.startedAt period FROM study_group sg ORDER BY period")
	List<StudyGroup> findAllByOrderByPeriod();


}
