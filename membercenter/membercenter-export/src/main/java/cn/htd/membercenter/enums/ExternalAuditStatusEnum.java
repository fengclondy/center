package cn.htd.membercenter.enums;

/**
 * 
 * @author li.jun
 * @desc 超级经理人展示状态 1.运营待审核 2.运营审核不通过 3.运营审核终止/驳回 4.供应商待审核
 *       5.供应商审核通过（或者公海会员运营审核通过默认通过）
 */
public enum ExternalAuditStatusEnum {
	OPERATE_AUDIT_PENDING("1", "运营待审核"), OPERATE_AUDIT_NOT_PASS("2", "运营审核不通过"), OPERATE_AUDIT_REJECT("3",
			"运营审核终止/驳回"), SUPPLIERE_UDIT_PENDING("4", "供应商待审核"), SUPPLIERE_AUDIT_PASS("5", "审核通过");

	private String code;
	private String value;

	ExternalAuditStatusEnum(String code, String value) {
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
