package cn.htd.membercenter.common.constant;

public enum MemberCenterCodeEnum {

	// 会员中心状态返回码
	BUSINESS_RELATIONSHIP("13000", "买家和卖家有经营关系"), NO_BUSINESS_RELATIONSHIP(
			"13001", "买家和卖家没有经营关系"), SUCCESS("00000", "成功编码"), ERROR("99999",
			"异常编码"), NO_DATA_CODE("13999", "没查询到数据");
	private MemberCenterCodeEnum(String code, String Msg) {
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

	// 获取枚举code对应名字方法
	public static String getMemberCenterCodeName(String code) {
		for (MemberCenterCodeEnum memberCenterCodeEnum : MemberCenterCodeEnum
				.values()) {
			if (memberCenterCodeEnum.getCode().equals(code)) {
				return memberCenterCodeEnum.Msg;
			}
		}
		return "";
	}
}
