package cn.htd.pricecenter.enums;


public enum PriceTypeEnum {
	SALE_PRICE(0, "销售价"), 
	LADDER_PRICE(1, "阶梯价"),
	GROUP_PRICE(2, "分组价"), 
	MEMBER_LEVEL_PRICE(3, "等级价"),
	AREA_PRICE(4, "区域价");

	private int code;
	private String label;

	PriceTypeEnum(int code, String label) {
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
			for (PriceTypeEnum userType : PriceTypeEnum.values()) {
				if (userType.getCode() == code) {
					return userType.label;
				}
			}
		}
		return "";
	}
	
}
