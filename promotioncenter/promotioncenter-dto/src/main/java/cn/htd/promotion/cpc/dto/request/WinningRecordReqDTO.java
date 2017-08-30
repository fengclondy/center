package cn.htd.promotion.cpc.dto.request;

import org.hibernate.validator.constraints.NotEmpty;

public class WinningRecordReqDTO extends GenricReqDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2051764512994485769L;

	/**
	 * 粉丝号
	 */
	@NotEmpty(message = "memberNo不能为空")
	private String memberNo;

	/**
	 * 开始位置
	 */
	@NotEmpty(message = "startNo不能为空")
	private Integer startNo;

	/**
	 * 结束位置
	 */
	@NotEmpty(message = "endNo不能为空")
	private Integer endNo;

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public Integer getStartNo() {
		return startNo;
	}

	public void setStartNo(Integer startNo) {
		this.startNo = startNo;
	}

	public Integer getEndNo() {
		return endNo;
	}

	public void setEndNo(Integer endNo) {
		this.endNo = endNo;
	}
}
