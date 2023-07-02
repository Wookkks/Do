package haza.demo.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Member {

	private Long memberNo;
	private String name;
	private String id;
	private String password;
	
	public Member() {}
	
	public Member(String name, String id, String password) {
		this.name = name;
		this.id = id;
		this.password = password;
	}
}
