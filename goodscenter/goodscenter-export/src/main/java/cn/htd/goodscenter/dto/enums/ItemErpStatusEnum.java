package cn.htd.goodscenter.dto.enums;

public enum ItemErpStatusEnum {


	WAITING("1", "待下行"), DOWNING("2", "下行中"), FAIL("3", "下行失败"), DOWNED("4", "已下行");

	private String code;
	private String label;

	ItemErpStatusEnum(String code, String label) {
		this.code = code;
		this.label = label;
	}

	public static ItemErpStatusEnum getEnumBycode(String code) {
		if (code != null) {
			for (ItemErpStatusEnum userType : ItemErpStatusEnum.values()) {
				if (userType.getCode().equals(code)) {
					return userType;
				}
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

}
