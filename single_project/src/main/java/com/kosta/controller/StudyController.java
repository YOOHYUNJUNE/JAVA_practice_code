package com.kosta.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kosta.domain.StudyGroupDTO;
import com.kosta.entity.User;
import com.kosta.service.StudyGroupService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/study")
@RequiredArgsConstructor
public class StudyController {
	
	private final StudyGroupService studyGroupService;
	
	@GetMapping("")
	public String allStudyGroupListPage(Model model, @RequestParam(name="sort", required = false) Integer sort) {
		List<StudyGroupDTO> list = studyGroupService.findAll(sort);
		model.addAttribute("list", list);
		return "study/list";
	}
	
	@GetMapping("/{id}")
	public String studyGroupDetailPage(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal User user) {
		StudyGroupDTO sg = studyGroupService.findStudyGroupById(id, user);
		model.addAttribute("studyGroup", sg);
		return "study/detail";
	}
}
