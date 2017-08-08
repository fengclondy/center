package cn.htd.goodscenter.dto.enums;

/**
 * 
 * <p>
 * Description:渠道编码: 10 内部供应商 20 外部供应商 3010 京东商品＋
 * </p>
 */
public enum ProductChannelEnum {

	INTERNAL_SUPPLIER("10", "内部供应商"),
	EXTERNAL_SUPPLIER("20", "外部供应商"),
	JD_PRODUCT("3010", "京东商品+");

	private String code;
	private String label;

	ProductChannelEnum(String code, String label) {
		this.code = code;
		this.label = label;
	}

	public static ProductChannelEnum getEnumBycode(String code) {
		if (code != null) {
			for (ProductChannelEnum userType : ProductChannelEnum.values()) {
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
