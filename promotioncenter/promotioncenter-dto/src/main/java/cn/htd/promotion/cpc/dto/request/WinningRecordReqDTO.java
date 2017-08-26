package cn.htd.promotion.cpc.dto.request;

public class WinningRecordReqDTO extends GenricReqDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2051764512994485769L;

	/**
	 * 粉丝号
	 */
	private String memberNo;

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
}
