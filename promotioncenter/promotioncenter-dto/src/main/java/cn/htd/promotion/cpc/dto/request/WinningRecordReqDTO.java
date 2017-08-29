package cn.htd.promotion.cpc.dto.request;

public class WinningRecordReqDTO extends GenricReqDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2051764512994485769L;

	/**
	 * 粉丝号
	 */
	private String memberNo;

	/**
	 * 开始位置
	 */
	private Integer startNo;

	/**
	 * 结束位置
	 */
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
