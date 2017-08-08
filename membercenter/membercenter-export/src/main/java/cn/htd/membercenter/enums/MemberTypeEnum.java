package cn.htd.membercenter.enums;

public enum MemberTypeEnum {

	GUARANTEE_MEMBER("3", "担保会员"), NONE_MEMBER("1", "非会员"), REAL_MEMBER("2", "正式会员");

	private String code;
	private String value;

	MemberTypeEnum(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public static MemberTypeEnum getEnumByCode(String code) {
		if (code != null) {
			for (MemberTypeEnum memberType : MemberTypeEnum.values()) {
				if (code.equals(memberType.getCode())) {
					return memberType;
				}
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

}
