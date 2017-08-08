package cn.htd.membercenter.enums;

public enum AuditStatusEnum {
	PENDING_AUDIT("0", "待审核"), PASSING_AUDIT("1", "审核通过"), REJECT_AUDIT("2", "驳回");

	private String code;
	private String value;

	AuditStatusEnum(String code, String value) {
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
