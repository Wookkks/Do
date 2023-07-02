package haza.demo.repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import haza.demo.domain.WorkList;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class JdbcListRepository implements ListRepository {

	private final JdbcTemplate jdbcTemplate;

	public JdbcListRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper<WorkList> listRowMapper() {
		return new RowMapper<WorkList>() {
			@Override
			public WorkList mapRow(ResultSet rs, int rowNum) throws SQLException {
				WorkList workList = new WorkList();
				workList.setWorkNo(rs.getLong("WORKNO"));
				workList.setDate(rs.getDate("DATE"));
				workList.setWork(rs.getString("WORK"));
				workList.setMemo(rs.getString("MEMO"));
				workList.setWorkyn(rs.getBoolean("WORKYN"));
				return workList;
			}
		};
	}
	
	@Override
	public WorkList save(WorkList workList) {
	    SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
	    jdbcInsert.withTableName("LIST").usingGeneratedKeyColumns("WORKNO");
	    Map<String, Object> parameters = new HashMap<>(); 
	    parameters.put("DATE", workList.getDate());
	    parameters.put("WORK", workList.getWork());
	    parameters.put("MEMO", workList.getMemo());
	    parameters.put("WORKYN", workList.getWorkyn());
	    Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
	    workList.setWorkNo(key.longValue());
	    return workList;
	 }

	@Override
	public Optional<WorkList> findByNo(Long workNo) {
		String sql = "SELECT * FROM LIST WHERE WORKNO = ?";
		List<WorkList> result = jdbcTemplate.query(sql, listRowMapper(), workNo);
		return result.stream().findAny();
	}
	@Override
	public List<WorkList> today() {
		String sql = "SELECT * FROM LIST WHERE DATE = CURRENT_DATE()";
		List<WorkList> result = jdbcTemplate.query(sql, listRowMapper());
		return result;
	}
	@Override
	public List<WorkList> yestetday(){
		String sql = "SELECT * FROM LIST WHERE DATE = CURRENT_DATE() - INTERVAL 1 DAY";
		List<WorkList> result = jdbcTemplate.query(sql, listRowMapper());
		return result;
	}
	@Override
	public List<WorkList> tomorrow(){
		String sql = "SELECT * FROM LIST WHERE DATE = CURRENT_DATE() + INTERVAL 1 DAY";
		List<WorkList> result = jdbcTemplate.query(sql, listRowMapper());
		return result;
	}
	@Override
	public List<WorkList> findByDate(String date) {
		String sql = "SELECT * FROM LIST WHERE DATE = ?";
		List<WorkList> result = jdbcTemplate.query(sql, listRowMapper(), date);
		return result;
	}

	@Override
	public WorkList update(Long workNo, WorkList workList) {
		String sql = "UPDATE LIST SET DATE = ?, WORK = ?, MEMO = ?, WORKYN = ? WHERE WORKNO = ?";
		jdbcTemplate.update(sql, workList.getDate(), workList.getWork(), 
				workList.getMemo(), workList.getWorkyn(), workNo);
		workList.setWorkNo(workNo);
		return findByNo(workNo).get();
	}

	@Override
	public void deleteWork(Long workNo) {
		String sql = "DELETE FROM LIST WHERE WORKNO = ?";
		jdbcTemplate.update(sql, workNo);
	}

	@Override
	public List<WorkList> findAll() {
		return jdbcTemplate.query("SELECT * FROM LIST", listRowMapper());
	}

	@Override
	public List<WorkList> search (String work) {
		String sql = "SELECT * FROM LIST WHERE WORK LIKE ?";
		String search = "%" + work + "%";
		return jdbcTemplate.query(sql, listRowMapper(), search);
	}
	
	public List<WorkList> find() {
		return jdbcTemplate.query("SELECT * FROM LIST", listRowMapper());
	}

	@Override
	public List<WorkList> manageTrue() {
		List<WorkList> workTrue = jdbcTemplate.query("SELECT * FROM LIST WHERE WORKYN = TRUE" , listRowMapper());
		return workTrue;
	}
	
	@Override
	public List<WorkList> manageFalse() {
		List<WorkList> workFalse = jdbcTemplate.query("SELECT * FROM LIST WHERE WORKYN = FALSE" , listRowMapper());
		return workFalse;
	}


	
}
