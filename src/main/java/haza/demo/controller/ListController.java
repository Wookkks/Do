package haza.demo.controller;

import java.time.LocalDate;
import java.util.List;

import haza.demo.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import haza.demo.domain.WorkList;
import haza.demo.repository.ListRepository;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequestMapping("/list")
public class ListController {
	
	private final ListRepository repository;
	public ListController(ListRepository repository) {
		this.repository = repository;
	}

	// DAILY =============================================================================
	@GetMapping("/daily")
	public String getDaily(@RequestParam(required = false) String date , Model model, HttpSession session) {
		Long memberNo = (Long)session.getAttribute("memberNo");
		if(date == null || date.isEmpty()) {
			date = LocalDate.now().toString();
		}
	    List<WorkList> work = repository.findAll(memberNo, date);
	    model.addAttribute("workList", work);
	    return "/list/daily";
	}
	
	@GetMapping("/date")
	public String Date(@RequestParam String date , Model model) {
		List<WorkList> work = repository.findByDate(date);
		model.addAttribute("workList", work);
		model.addAttribute("date", date);
		return "list/daily";
	}
	
	@GetMapping("/manage")
	public String manage(Model model, HttpSession session) {
		Long memberNo = (Long)session.getAttribute("memberNo");
		List<WorkList> workTrue = repository.manageTrue(memberNo);
		List<WorkList> workFalse = repository.manageFalse(memberNo);
		model.addAttribute("workTrue", workTrue);
		model.addAttribute("workFalse", workFalse);
		return "list/manage";
	}
	
	@GetMapping("/search")
	public String search(@RequestParam String search, Model model, HttpSession session){
		Long memberNo = (Long)session.getAttribute("memberNo");
		List<WorkList> work = repository.search(search, memberNo);
		model.addAttribute("searchWord", search);
		model.addAttribute("search", work);
		return "list/searchList";
	}
	@GetMapping("/weekly")
	public String weekly(Model model, HttpSession session) {
		Long memberNo = (Long)session.getAttribute("memberNo");
		List<WorkList> yesterday = repository.yestetday(memberNo);
		List<WorkList> today = repository.today(memberNo);
		List<WorkList> tomorrow = repository.tomorrow(memberNo);
		model.addAttribute("yesterday", yesterday);
		model.addAttribute("today", today);
		model.addAttribute("tomorrow", tomorrow);
		return "list/weekly";
	}
	@GetMapping("/add")
	public String add(HttpSession session) {
		Long memberNo = (Long)session.getAttribute("memberNo");
		return "list/addList";
	}

	@PostMapping("/add")
	public String addList(@ModelAttribute WorkList workList, HttpSession session, RedirectAttributes attributes) {
		Long memberNo = (Long)session.getAttribute("memberNo");
	    repository.save(workList, memberNo);
	    attributes.addAttribute("work", workList.getWork());
	    return "redirect:/list/daily";
	}
	@GetMapping("/delete/{workNo}")
	public String delete(@PathVariable Long workNo) {
		repository.deleteWork(workNo);
		return "redirect:/list/daily";
	}
	
	@GetMapping("/edit/{workNo}")
	public String editForm(@PathVariable Long workNo, Model model) {
		WorkList work = repository.findByNo(workNo).get();
		model.addAttribute("work", work);
		return "list/editList";
	}
	
	@PostMapping("/edit/{workNo}")
	public String edit(@PathVariable Long workNo, WorkList workList) {
		repository.update(workNo, workList);
		return "redirect:/list/daily";
	}
	@GetMapping("/todo/{workNo}")
	public String todo(@PathVariable Long workNo, Model model) {
		WorkList work = repository.findByNo(workNo).get();
		model.addAttribute("work", work);
		return "list/todo";
	}
	
	// WEEKLY ===============================================================================
	@GetMapping("/add/week")
	public String weekAdd() {
		return "list/addList";
	}
	@PostMapping("/add/week")
	public String weekAddList(@ModelAttribute WorkList workList, HttpSession session, RedirectAttributes attributes) {
		Long memberNo = (Long)session.getAttribute("memberNo");
	    repository.save(workList, memberNo);
	    attributes.addAttribute("work", workList.getMemo());
	    return "redirect:/list/weekly";
	}
	@GetMapping("/delete/week/{workNo}")
	public String weekDelete(@PathVariable Long workNo) {
		repository.deleteWork(workNo);
		return "redirect:/list/weekly";
	}
	@GetMapping("/edit/week/{workNo}")
	public String weekEditForm(@PathVariable Long workNo, Model model) {
		WorkList work = repository.findByNo(workNo).get();
		model.addAttribute("work", work);
		return "list/editList";
	}
	@PostMapping("/edit/week/{workNo}")
	public String weekEdit(@PathVariable Long workNo, WorkList workList, Model model) {
		WorkList work = repository.update(workNo, workList);
		model.addAttribute("work", work);
		return "redirect:/list/weekly";
	}
	@GetMapping("/todo/week/{workNo}")
	public String weekTodo(@PathVariable Long workNo, Model model) {
		WorkList work = repository.findByNo(workNo).get();
		model.addAttribute("work", work);
		return "list/todo";
	}
	
	// MANAGE =================================================================================
	@GetMapping("/delete/manage/{workNo}")
	public String manageDelete(@PathVariable Long workNo) {
	   repository.deleteWork(workNo);
	   return "redirect:/list/manage";
	}
	@GetMapping("/edit/manage/{workNo}")
	public String manageEditForm(@PathVariable Long workNo, Model model) {
		WorkList work = repository.findByNo(workNo).get();
		model.addAttribute("work", work);
		return "list/editList";
	}
	@PostMapping("/edit/manage/{workNo}")
	public String manageEdit(@PathVariable Long workNo, WorkList workList, Model model) {
		WorkList work = repository.update(workNo, workList);
		model.addAttribute("work", work);
		return "redirect:/list/manage";
	}

}
