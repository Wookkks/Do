package haza.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import haza.demo.domain.Member;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
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
	  log.info("member.getMemberNo() : {}", member.getMemberNo());
	  log.info("saveMember.getMemberNo() : {}", saveMember.getMemberNo());
	  assertThat(member.getMemberNo()).isEqualTo(saveMember.getMemberNo());
	}

}
