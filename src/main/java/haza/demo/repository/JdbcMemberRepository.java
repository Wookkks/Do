package haza.demo.repository;

import haza.demo.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
public class JdbcMemberRepository implements MemberRepository{
	
	private final JdbcTemplate jdbcTemplate;
	
	public JdbcMemberRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private RowMapper<Member> memberRowMapper(){
		return new RowMapper<Member>() {
			@Override
			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
				Member member = new Member();
				member.setMemberNo(rs.getLong("M_NO"));
				member.setName(rs.getString("M_NAME"));
				member.setUsername(rs.getString("M_ID"));
				member.setPassword(rs.getString("M_PWD"));
				member.setRole(rs.getString("M_ROLE"));
				member.setJoinDate(rs.getDate("M_joinDate"));
				return member;
			}
		};
	}

	@Override
	public Member memberSave(Member member) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("MEMBER")
				.usingGeneratedKeyColumns("M_NO")
				.usingColumns("M_NAME", "M_ID", "M_PWD", "M_ROLE");

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("M_NAME", member.getName());
		parameters.put("M_ID", member.getUsername());
		parameters.put("M_PWD", member.getPassword());
		parameters.put("M_ROLE", "USER");

		Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
		member.setMemberNo(key.longValue());
		return member;
	}

	@Override
	public Optional<Member> findByUsernameAndPassword(String username, String password) {
		String sql = "SELECT * FROM MEMBER WHERE M_ID = ? AND M_PWD = ?";
		List<Member> result = jdbcTemplate.query(sql, memberRowMapper(), username, password);
		return result.stream().findAny();
	}

}
