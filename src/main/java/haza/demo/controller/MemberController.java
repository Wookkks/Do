package haza.demo.controller;

import haza.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import haza.demo.domain.Member;
import haza.demo.repository.MemberRepository;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping("/login")
	public String login(HttpSession session) {
		Long id = (Long)session.getAttribute("memberNo");
		if(id != null) {
			return "redirect:/list/daily";
		}
		return "member/login";
	}

	@PostMapping("/login")
	public String login(Member member, HttpSession session) {
		String username = member.getUsername();
		String password = member.getPassword();
		Long memberNo = memberService.findMember(username, password);
		if(memberNo == null) {
			return "redirect:/member/login";
		}
		session.setAttribute("memberNo", memberNo);
		session.setMaxInactiveInterval(1800);
		return "redirect:/list/daily";
	}

	@GetMapping("/join")
	public String join() {
		return "member/join";
	}
	
	@PostMapping("/join")
	public String join(Member member, HttpSession session) {
		memberService.save(member);
		session.setAttribute("memberNo", member.getMemberNo());
		session.setMaxInactiveInterval(1800);
		return "member/join-result";
	}

	@PostMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/member/login";
	}
}
