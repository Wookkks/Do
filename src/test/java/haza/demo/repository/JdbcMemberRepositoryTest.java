package haza.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import haza.demo.domain.Member;

@SpringBootTest
class JdbcMemberRepositoryTest {
	
	@Autowired JdbcMemberRepository repository;
	
	@Test
	@Transactional
	void save() {
	  Member member = new Member();
	  member.setName("Test");
	  member.setId("memo");
	  member.setPassword("1234");   
	  Member saveMember = repository.memberSave(member);
	  assertThat(member.getMemberNo()).isEqualTo(saveMember.getMemberNo());
	}

}
