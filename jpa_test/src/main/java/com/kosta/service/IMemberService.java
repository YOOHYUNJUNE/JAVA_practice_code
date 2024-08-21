package com.kosta.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.entity.Member;
import com.kosta.repository.MemberRepository;


@Service
public class IMemberService implements MemberService {
	
	@Autowired
	private MemberRepository mp;

	// 모든 회원 리스트 가져오기
	@Override
	public List<Member> getAll() throws Exception {
		return mp.findAll();
	}

	// 회원 추가(저장)
	@Override
	public void addMember(Member member) throws Exception {
		mp.save(member);
	}

	// 회원 삭제
	@Override
	public void deleteMemberById(int id) throws Exception {
		mp.deleteById(id);
	}

	// 회원 찾기(수정을 위해)
	@Override
	public Member getMemberById(int id) throws Exception {
		Optional<Member> om = mp.findById(id);
		Member member = om.orElseThrow(() -> new Exception("회원 정보 없음"));
		return member;			
	}

	// 회원 수정
	@Override
	public void editMember(Member member) throws Exception {
		Member eMember = getMemberById(member.getId()); // 수정하려는 회원의 ID를 가져옴
		eMember.setName(member.getName());
		mp.save(eMember);
	}

	
	// 회원 검색
	@Override
	public List<Member> searchMember(String keyword) throws Exception {
		return mp.findByNameContains(keyword);
	}

	
	
	
	
	
	
	
}
