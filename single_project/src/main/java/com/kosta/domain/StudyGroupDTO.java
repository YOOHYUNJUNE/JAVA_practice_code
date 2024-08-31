package com.kosta.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.kosta.entity.StudyGroup;
import com.kosta.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class StudyGroupDTO {
	private Long id;
	private String name;
	private String description;
	private User leader;
	private List<UserDTO> memberList;
	private String startedAt;
	private String finishedAt;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private boolean showManage = false;
	private boolean isJoined = false;
	
	public StudyGroup setStudyGroup() {
		StudyGroup sg = new StudyGroup();
		sg.setId(id);
		sg.setName(name);
		sg.setDescription(description);
		sg.setLeader(leader);
		sg.setStartedAt(LocalDate.parse(startedAt, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay());
		sg.setFinishedAt(LocalDate.parse(finishedAt, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay());
		sg.setCreatedAt(createdAt);
		sg.setUpdatedAt(updatedAt);
		return sg;
	}

	public static StudyGroupDTO setStudyGroupDTO(StudyGroup sg) {
		return StudyGroupDTO.builder()
		.id(sg.getId())
		.name(sg.getName())
		.description(sg.getDescription())
		.leader(sg.getLeader())
		.startedAt(sg.getStartedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
		.finishedAt(sg.getFinishedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
		.createdAt(sg.getCreatedAt())
		.updatedAt(sg.getUpdatedAt())
		.build();
	}
	
	
}
