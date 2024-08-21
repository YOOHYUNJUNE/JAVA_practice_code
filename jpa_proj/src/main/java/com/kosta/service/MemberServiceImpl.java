package com.kosta.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.entity.Member;
import com.kosta.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {

	// mapper가 아니라 repository에 전달 : repo와 부모의 메소드가 많기 때문에 따로 메소드 생성할 필요 없음
	@Autowired
	private MemberRepository memberRepository;
	
	
	@Override
	public List<Member> getAll() throws Exception {		
		return memberRepository.findAll();
	}
	

	@Override
	public void insertMember(Member member) throws Exception {
		memberRepository.save(member);		
	}
	

	@Override
	public void deleteMemberById(int id) throws Exception {
		memberRepository.deleteById(id);		
	}


	@Override
	public Member getMemberById(int id) throws Exception {
		Optional<Member> optMember = memberRepository.findById(id); 
		Member member = optMember.orElseThrow(() -> new Exception("없음")); // null이 있는 경우
		return member;
	}


	@Override
	public void modifyMember(Member member) throws Exception {
		Member exMember = getMemberById(member.getId()); // 이미 존재하는 멤버
		exMember.setName(member.getName()); // set
		memberRepository.save(exMember);
	}


	@Override
	public List<Member> searchMember(String keyword) throws Exception {
		return memberRepository.findByNameContains(keyword);
	}

	
	
	
}
