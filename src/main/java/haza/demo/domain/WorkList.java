package haza.demo.domain;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WorkList {
	private Long workNo;
	private Date date;
	private String work;
	private String memo;
	private Boolean workyn = false;
	
	public WorkList() {}
	
	public WorkList(String work, String memo, boolean workyn) {
		this.work = work;
		this.memo = memo;
		this.workyn = workyn;
	}
}
