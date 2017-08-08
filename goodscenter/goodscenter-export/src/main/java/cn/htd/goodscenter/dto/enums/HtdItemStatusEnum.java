package cn.htd.goodscenter.dto.enums;
/**
 * HTD商品状态
 * @author zhangxiaolong
 *
 */
public enum HtdItemStatusEnum {
	AUDITING(0, "待审核"), 
	PASS(1, "审核通过"),
	REJECTED(2, "审核驳回"), 
	ERP_STOCKPRICE_OR_OUTPRODUCTPRICE(3, "待ERP上行库存及价格或外部商品品类价格待映射"),
	NOT_SHELVES(4, "未上架"),
	SHELVED(5, "已上架"), 
	DELETED(6, "已删除");

	private int code;
	private String label;

	HtdItemStatusEnum(int code, String label) {
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
			for (HtdItemStatusEnum userType : HtdItemStatusEnum.values()) {
				if (userType.getCode() == code) {
					return userType.label;
				}
			}
		}
		return "";
	}


}
