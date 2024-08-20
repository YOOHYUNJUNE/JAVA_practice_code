package com.kosta.service;

import java.util.List;

import com.kosta.entity.Member;

public interface MemberService {

	List<Member> getAll() throws Exception;

	void insertMember(Member member) throws Exception;

}
