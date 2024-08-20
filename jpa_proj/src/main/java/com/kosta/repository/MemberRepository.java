package com.kosta.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosta.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

	// 이름으로 찾는 메서드 생성
	Optional<Member> findByName(String name);
	
	
}
