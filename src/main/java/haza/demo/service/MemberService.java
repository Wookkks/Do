package haza.demo.service;

import haza.demo.domain.Member;
import haza.demo.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member save(Member member) {
        Member saveMember = memberRepository.memberSave(member);
        return saveMember;
    }

    public Long findMember(String username, String password) {
        Member findMember = memberRepository.findByUsernameAndPassword(username, password).get();
        return findMember.getMemberNo();
    }
}
