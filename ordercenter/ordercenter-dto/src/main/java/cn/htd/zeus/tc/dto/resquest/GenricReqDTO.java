/**
 * 
 */
package cn.htd.zeus.tc.dto.resquest;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author ly
 *
 */
public class GenricReqDTO {
	
	@NotEmpty(message = "messageId不能为空")
	private String messageId = null;
	
	private Integer startTime;
	
	private Integer endTime;
	
	private Integer page;
	
	private Integer limit;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

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

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
}
