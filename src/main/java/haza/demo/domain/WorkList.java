package haza.demo.domain;

import java.sql.Date;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class WorkList {
	private Long workNo;
	private Date date;
	private String work;
	private String memo;
	private Boolean workYn = false;
	
	public WorkList(String work, String memo, boolean workYn) {
		this.work = work;
		this.memo = memo;
		this.workYn = workYn;
	}
}
