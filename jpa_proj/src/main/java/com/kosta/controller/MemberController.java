package com.kosta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	
	// 회원 삭제
	@GetMapping("/delete/{id}")
	public String deleteMember(@PathVariable("id") int id) throws Exception {
		ms.deleteMemberById(id);
		return "redirect:/list";
	}
	
	
	// 회원 수정 화면
	@GetMapping("/modify/{id}")
	public String modifyPage(@PathVariable("id") int id, Model model) throws Exception {
		Member member = ms.getMemberById(id);
		model.addAttribute("member", member);
		return "member/madd";
	}
	
	// 회원 수정
	@PostMapping("/modify")
	public String modifyMember(Member member) throws Exception {
		ms.modifyMember(member);
		return "redirect:/list";
	}
	
	
	// 회원 검색
	@GetMapping("/search")
	public String searchMember(@RequestParam("keyword") String keyword, Model model) throws Exception {
		List<Member> memberSearchResult = ms.searchMember(keyword);
		model.addAttribute("list", memberSearchResult);
		return "member/mlist";
	}
	
	
	
	
}
