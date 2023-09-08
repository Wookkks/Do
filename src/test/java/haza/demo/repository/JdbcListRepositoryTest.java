//package haza.demo.repository;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import haza.demo.domain.WorkList;
//
//@SpringBootTest
//class JdbcListRepositoryTest {
//
//	@Autowired JdbcListRepository repository;
//
//	@Test
//	@Transactional
//	void save() {
//		WorkList list = new WorkList();
//		list.setWork("addTest");
//		list.setMemo("addMemo");
//		WorkList saveList = repository.save(list);
//		assertThat(list.getWorkNo()).isEqualTo(saveList.getWorkNo());
//	}
//
//	@Test
//	void update() {
//		WorkList before = new WorkList();
//		before.setWork("beforeUpdateWork");
//		before.setMemo("beforeUpdateMemo");
//		repository.save(before);
//		WorkList after = repository.update(before.getWorkNo(),
//				new WorkList("afterUpdateWork","afterUpdateMemo", true));
//		assertThat(before.getWorkNo()).isEqualTo(after.getWorkNo());
//	}
//
//	@Test
//	@Transactional
//	void search() {
//		WorkList listOne = new WorkList("searchOne", "memo", true);
//		WorkList listTwo = new WorkList("searchTwo", "memo", true);
//		WorkList listThree = new WorkList("searchThree", "memo", true);
//		repository.save(listOne);
//		repository.save(listTwo);
//		repository.save(listThree);
//		List<WorkList> result = repository.search("search");
//		assertThat(result.size()).isEqualTo(3);
//	}
//	@Test
//	void findByNo() {
//		WorkList list = new WorkList();
//		repository.save(list);
//		WorkList save = repository.findByNo(list.getWorkNo()).get();
//		assertThat(save.getWorkNo()).isEqualTo(list.getWorkNo());
//	}
//
//	@Test
//	void deleteWork() {
//		List<WorkList> beforeDelete = repository.findAll();
//		WorkList list = new WorkList("deleteTest", "memo", false);
//		repository.save(list);
//		Long workNo = list.getWorkNo();
//		repository.deleteWork(workNo);
//		List<WorkList> afterDelete = repository.findAll();
//		assertThat(beforeDelete.size()).isEqualTo(afterDelete.size());
//	}
//}
