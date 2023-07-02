package haza.demo.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class Member {

	private Long memberNo;
	private String name;
	private String username;
	private String password;
	private String role;
	private Date joinDate;

	public Member(String name, String username, String password) {
		this.name = name;
		this.username = username;
		this.password = password;
	}

	public Member(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
