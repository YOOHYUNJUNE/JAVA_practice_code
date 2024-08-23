package com.kosta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.entity.User;
import com.kosta.service.MemberService;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/blog/*")
@RequiredArgsConstructor
public class MemberController {

	@Autowired
	MemberService ms;
	
	
	// 전체 페이지
	@GetMapping("/list")
	public ModelAndView listPage() throws Exception {
		ModelAndView mv = new ModelAndView("/mlist");
		List<User> memberList = ms.getAll();
		mv.addObject("list", memberList);
		return mv;
	}
	
	
	// 회원 추가 페이지
	@GetMapping("/add")
	public ModelAndView addPage() throws Exception {
		ModelAndView mv = new ModelAndView("/madd");
		return mv;
	}
	
	
	// 회원 추가
	@PostMapping("/add")
	public String addMember(User member) throws Exception {	
//		if (member.getName().isEmpty()) {
//			member.setName(null);
//		}
		ms.addMember(member);
		return "redirect:/blog/list";
	}
	
	
	// 회원 삭제
	@GetMapping("/delete/{id}")
	public String deleteMember(@PathVariable("id") Long id) throws Exception {
		ms.deleteMemberById(id);
		return "redirect:/blog/list";
	}
	
	
	// 회원 수정 페이지
	@GetMapping("/edit/{id}")
	public String editPage(@PathVariable("id") Long id, Model model) throws Exception {
		User mb = ms.getMemberById(id);
		model.addAttribute("member", mb);
		return "/madd";
	}
	
	
	// 회원 수정
	@PostMapping("/edit")
	public String editMember(User member) throws Exception {
		ms.editMember(member);
		return "redirect:/blog/list";
	}
	
	
	// 회원 찾기
	@GetMapping("/search")
	public String searchMember(@RequestParam("keyword") String keyword, Model model) throws Exception {
		List<User> memberSearch = ms.searchMember(keyword);
		model.addAttribute("list", memberSearch);
		return "/mlist";
	}
	
	
	
	
	
}
