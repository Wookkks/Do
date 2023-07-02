package haza.demo.controller;

import haza.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
			return "/list/daily";
		}
		return "member/login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute Member member, HttpSession session) {
		String username = member.getUsername();
		String password = member.getPassword();
		Long id = memberService.findMember(username, password);
		if(id == null) {
			return "redirect:/member/login";
		}
		session.setAttribute("memberNo", id);
		return "/list/daily";
	}

	@GetMapping("/join")
	public String join() {
		return "member/join";
	}
	
	@PostMapping("/join")
	public String join(@ModelAttribute Member member) {
		memberService.save(member);
		return "member/join-result";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "member/login";
	}

}
