package cn.htd.goodscenter.dto.enums;
/**
 * HTD商品状态
 * @author zhangxiaolong
 *
 */
public enum AuditStatusEnum {
	AUDIT(0, "待审核"),
	PASS(1, "审核通过"),
	REJECTED(2, "新增审核不通过"),
	MODIFY_AUDIT(3, "修改待审核"),
	MODIFY_REJECTED(4, "修改审核不通过");

	private int code;
	private String label;

	AuditStatusEnum(int code, String label) {
		this.code = code;
		this.label = label;
	}

	public int getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}


	public static String getEnumBycode(Integer code) {
		if (code != null) {
			for (AuditStatusEnum userType : AuditStatusEnum.values()) {
				if (userType.getCode() == code) {
					return userType.label;
				}
			}
		}
		return "";
	}


}
