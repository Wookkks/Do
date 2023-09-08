package haza.demo.repository;

import java.util.Optional;

import haza.demo.domain.Member;

public interface MemberRepository {
	
	// 회원가입
	Member memberSave(Member member);

	Optional<Member> findByUsernameAndPassword(String username, String password);

}
