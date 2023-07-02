package haza.demo.repository;

import java.util.List;
import java.util.Optional;

import haza.demo.domain.WorkList;
import org.springframework.web.bind.annotation.SessionAttribute;

public interface ListRepository {
	
	// 일정 추가
	WorkList save(WorkList workList);
		
	// work_no 검색
	Optional<WorkList> findByNo(Long workNo);
		
	// 데일리 화면
	List<WorkList> findAll(Long memberNo);

	// 검색
	List<WorkList> search(String work);
		
	// 수정
	WorkList update(Long workId, WorkList workList);
		
	// 삭제
	void deleteWork(Long workNo);
		
	// 일정 관리
	List<WorkList> manageTrue();
	List<WorkList> manageFalse();
	List<WorkList> findByDate(String date);
	
	// 최근 일정
	List<WorkList> yestetday();
	List<WorkList> today();
	List<WorkList> tomorrow();
		
	
}
