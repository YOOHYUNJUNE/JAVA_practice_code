package com.kosta.service;

import java.util.List;

import com.kosta.entity.Member;
import com.kosta.entity.User;

public interface MemberService {

	List<User> getAll() throws Exception;

	void addMember(User member) throws Exception;

	void deleteMemberById(Long id) throws Exception;

	User getMemberById(Long id) throws Exception;

	void editMember(User member) throws Exception;

	List<User> searchMember(String keyword) throws Exception;

}
