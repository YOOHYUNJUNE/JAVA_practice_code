package com.kosta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.entity.Member;
import com.kosta.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService ms;
	
	
	// 전체 페이지
	@GetMapping("/list")
	public ModelAndView listPage() throws Exception {
		ModelAndView mav = new ModelAndView("member/mlist");
		List<Member> mList = ms.getAll();
		mav.addObject("list", mList);
		return mav;
	}
	
	
	// 추가 페이지
	@GetMapping("/add")
	public ModelAndView addPage() throws Exception {
		ModelAndView mav = new ModelAndView("member/madd");
		return mav;
	}
	
	
	// 회원 추가
	@PostMapping("/add")
	public String addMember(Member member) throws Exception {
		ms.insertMember(member);
		return "redirect:/list";
	}
	
	
	
}
