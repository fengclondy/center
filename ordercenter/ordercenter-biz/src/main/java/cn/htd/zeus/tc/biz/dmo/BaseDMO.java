/**
 * 
 */
package cn.htd.zeus.tc.biz.dmo;

/**
 * @author ly
 *
 */
public class BaseDMO {

	private Integer startTime = 0;
	
	private Integer endTime = 0;
	
	private Integer start;
	
	private Integer rows;

	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}
}
