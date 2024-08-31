package com.kosta.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kosta.domain.StudyGroupDTO;
import com.kosta.entity.User;
import com.kosta.service.StudyGroupService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/leader")
@RequiredArgsConstructor
public class LeaderController {
	private final StudyGroupService studyGroupService;
	
	@GetMapping("")
	public String leaderStudyGroupListPage(@AuthenticationPrincipal User user, Model model) {
		List<StudyGroupDTO> list = studyGroupService.findByLeaderId(user.getId());
		model.addAttribute("list", list);
		return "leader/list";
	}
	
	@PatchMapping("/modify")
	public String modifyStudyGroup(@AuthenticationPrincipal User user, StudyGroupDTO studyGroupDTO) throws Exception {
		studyGroupService.modifyStudyGroup(studyGroupDTO, user.getId());
		return "redirect:/leader";
	}
	
	@DeleteMapping("/delete")
	public String deleteStudyGroup(@RequestParam("id") Long studyGroupId, @AuthenticationPrincipal User user) {
		studyGroupService.deleteStudyGroup(studyGroupId, user.getId());
		return "redirect:/mystudy";
	}
	
	@GetMapping("/{id}")
	public String studyGroupManagePage(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal User user) {
		StudyGroupDTO sg = studyGroupService.findStudyGroupById(id, user);
		model.addAttribute("studyGroup", sg);
		return "leader/detail";
	}
}
