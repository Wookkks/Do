package haza.demo.controller;

import java.time.LocalDate;
import java.util.List;

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
	public String getDaily(@RequestParam(required = false) String date , Model model) {
		if(date == null) 
			date = LocalDate.now().toString();
	    List<WorkList> work = repository.findByDate(date);
	    model.addAttribute("workList", work);
	    return "list/daily";
	}
	
	@GetMapping("/date")
	public String Date(@RequestParam String date , Model model) {
		List<WorkList> work = repository.findByDate(date);
		model.addAttribute("workList", work);
		model.addAttribute("date", date);
		return "list/daily";
	}
	
	@GetMapping("/manage")
	public String manage(Model model) {
		List<WorkList> workTrue = repository.manageTrue();
		List<WorkList> workFalse = repository.manageFalse();
		model.addAttribute("workTrue", workTrue);
		model.addAttribute("workFalse", workFalse);
		return "list/manage";
	}
	
	@GetMapping("/search")
	public String search(@RequestParam String search, Model model){
		List<WorkList> work = repository.search(search);
		model.addAttribute("search", work);
		model.addAttribute("searchword", search);
		return "list/searchList";
	}
	@GetMapping("/weekly")
	public String weekly(Model model) {
		List<WorkList> yesterday = repository.yestetday();
		List<WorkList> today = repository.today();
		List<WorkList> tomorrow = repository.tomorrow();
		model.addAttribute("yesterday", yesterday);
		model.addAttribute("today", today);
		model.addAttribute("tomorrow", tomorrow);
		return "list/weekly";
	}
	@GetMapping("/add")
	public String add() {
		return "list/addList";
	}

	@PostMapping("/add")
	public String addList(@ModelAttribute WorkList workList, RedirectAttributes attributes) {
	    repository.save(workList);
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
	public String weekAddList(@ModelAttribute WorkList workList, RedirectAttributes attributes) {
	    repository.save(workList);
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
