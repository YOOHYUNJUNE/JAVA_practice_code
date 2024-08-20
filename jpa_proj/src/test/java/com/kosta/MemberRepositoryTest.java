package com.kosta;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.kosta.entity.Member;
import com.kosta.repository.MemberRepository;

@DataJpaTest // jpa test임을 선언
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 DB로 테스트
public class MemberRepositoryTest {
	@Autowired
	MemberRepository memberRepository;
	
	@DisplayName("전체 회원 조회")
	@Sql("/data.sql") // 테스트 실행 전 sql문(insert) 실행
	@Test
	public void getAllMembers() {
		List<Member> allMembers = memberRepository.findAll();
		for(Member m : allMembers) {
			System.out.println(m);
		}
		assertThat(allMembers.size()).isEqualTo(3);
		
	}
	
	
	@DisplayName("특정 회원 조회")
	@Sql("/data.sql")
	@Test
	public void getMemberById() {
		Optional<Member> optionalMember = memberRepository.findById(2); // optional : null 여부까지 확인
		Member member = optionalMember.get();
		System.out.println(member);
		assertThat(member.getName()).isEqualTo("도우너");
	}
	
	
	@DisplayName("회원 추가")
	@Sql("/data.sql")
	@Test
	public void insertMember() {
		Member member = new Member();
		member.setId(4);
		member.setName("홍길동");
		Member savedMember = memberRepository.save(member);
		
		// 있는지 확인
//		assertThat(member).isEqualTo(memberRepository.findById(4).get());
		assertThat(savedMember).isEqualTo(memberRepository.findByName("홍길동").get());
	}
	
	
	
	@DisplayName("특정 회원 삭제")
	@Sql("/data.sql")
	@Test
	public void deleteMember() {
		memberRepository.deleteById(3); 
		assertThat(memberRepository.count()).isEqualTo(2);		
	}
	
	
	@DisplayName("특정 회원 수정")
	@Sql("/data.sql")
	@Test
	@Transactional // 생략가능(DataJpaTest에 이미 포함) : update기능
	public void updateMemberName() {
		Member member = memberRepository.findById(3).get();
		member.setName("고길동");		
		assertThat(memberRepository.findByName("고길동").get()).isEqualTo(member);
	}
	
	
	
	
}
