package cn.htd.zeus.tc.common.enums;

public enum MemberCenterEnum {

	//会员中心状态
	MEMBER_TYPE_BUYER("1","买家"),
	MEMBER_TYPE_SELLER("2","卖家"),
	NOT_HAS_INNER_COMAPANY_CERT("0","没有内部供应商身份"),
	HAS_INNER_COMAPANY_CERT("1","有内部供应商身份");
	
	private MemberCenterEnum(String code,String Msg){
		this.code = code;
		this.Msg = Msg;
	}
	
	private String code;
	private String Msg;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return Msg;
	}
	public void setMsg(String msg) {
		Msg = msg;
	}
	
}
