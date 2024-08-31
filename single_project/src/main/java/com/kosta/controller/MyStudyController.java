package com.kosta.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kosta.domain.StudyGroupDTO;
import com.kosta.entity.User;
import com.kosta.service.MyStudyGroupService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/mystudy")
@RequiredArgsConstructor
public class MyStudyController {
	private final MyStudyGroupService myStudyGroupService;
	
	@GetMapping("")
	public String allStudyGroupListPage(@AuthenticationPrincipal User user, Model model) {
		Long userId = user.getId();
		List<StudyGroupDTO> list = myStudyGroupService.findStudyGroupByMemberId(userId);
		model.addAttribute("list", list);
		return "mystudy/list";
	}
	
	@GetMapping("/{id}")
	public String studyGroupDetailPage(@PathVariable("id") Long id, Model model) {
		StudyGroupDTO sg = myStudyGroupService.findStudyGroupById(id);
		model.addAttribute("studyGroup", sg);
		return "mystudy/detail";
	}
	
	@GetMapping("/create")
	public String studyGroupCreateFormPage() {
		return "mystudy/create";
	}
	
	@PostMapping("/create")
	public String studyGroupCreate(@AuthenticationPrincipal User user, StudyGroupDTO sgDTO) throws Exception {
		myStudyGroupService.createStudyGroup(user.getId(), sgDTO);
		return "redirect:/leader";
	}
	
	@PostMapping("/join/{studyGroupId}")
	public String joinStudyGroup(@PathVariable("studyGroupId") Long studyGroupId, @AuthenticationPrincipal User user) throws Exception {
		myStudyGroupService.joinStudyGroup(studyGroupId, user.getId());
		return "redirect:/mystudy";
	}
	
	@DeleteMapping("/quit")
	public String quitStudyGroup(@RequestParam("id") Long studyGroupId, @AuthenticationPrincipal User user) {
		myStudyGroupService.quitStudyGroup(studyGroupId, user.getId());
		return "redirect:/mystudy";
	}
}
