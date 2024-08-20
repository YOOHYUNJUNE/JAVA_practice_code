package com.kosta.service;

import java.util.List;

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

}
