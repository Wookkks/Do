package haza.demo.repository;

import java.util.Optional;

import haza.demo.domain.Member;

public interface MemberRepository {
	
	// 회원가입
	Member memberSave(Member member);
	
	// 회원ID 조회 (필요할진 모르겠음)
	Optional<Member> findByNo(Long id);
}
