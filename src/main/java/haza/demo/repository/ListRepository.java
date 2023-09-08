package haza.demo.repository;

import java.util.List;
import java.util.Optional;

import haza.demo.domain.WorkList;
import org.springframework.web.bind.annotation.SessionAttribute;

public interface ListRepository {
	
	// 일정 추가
	WorkList save(WorkList workList, Long memberNo);
		
	// work_no 검색
	Optional<WorkList> findByNo(Long workNo);
		
	// 데일리 화면
	List<WorkList> findAll(Long memberNo, String date);

	// 검색
	List<WorkList> search(String work, Long memberNo);
		
	// 수정
	WorkList update(Long workId, WorkList workList);
		
	// 삭제
	void deleteWork(Long workNo);
		
	// 일정 관리
	List<WorkList> manageTrue(Long memberNo);
	List<WorkList> manageFalse(Long memberNo);
	List<WorkList> findByDate(String date);
	
	// 최근 일정
	List<WorkList> yestetday(Long memberNo);
	List<WorkList> today(Long memberNo);
	List<WorkList> tomorrow(Long memberNo);

	
}
