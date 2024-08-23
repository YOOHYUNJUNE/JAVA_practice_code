package com.kosta.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.entity.Member;
import com.kosta.entity.User;
import com.kosta.repository.MemberRepository;


@Service
public class IMemberService implements MemberService {
	
	@Autowired
	private MemberRepository mp;

	// 모든 회원 리스트 가져오기
	@Override
	public List<User> getAll() throws Exception {
		return mp.findAll();
	}

	// 회원 추가(저장)
	@Override
	public void addMember(User member) throws Exception {
		mp.save(member);
	}

	// 회원 삭제
	@Override
	public void deleteMemberById(Long id) throws Exception {
		mp.deleteById(id);
	}

	// 회원 찾기(수정을 위해)
	@Override
	public User getMemberById(Long id) throws Exception {
		Optional<User> om = mp.findById(id);
		User member = om.orElseThrow(() -> new Exception("회원 정보 없음"));
		return member;			
	}

	// 회원 수정
	@Override
	public void editMember(User member) throws Exception {
		User eMember = getMemberById(member.getId()); // 수정하려는 회원의 ID를 가져옴
		eMember.setName(member.getName());
		eMember.setInfo(member.getInfo());
		mp.save(eMember);
	}

	
	// 회원 검색
	@Override
	public List<User> searchMember(String keyword) throws Exception {
		return mp.findByNameContains(keyword);
	}

	
	
	
	
	
	
	
}
