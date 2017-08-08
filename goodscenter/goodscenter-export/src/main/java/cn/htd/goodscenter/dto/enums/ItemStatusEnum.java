package cn.htd.goodscenter.dto.enums;

/**
 * 
 * <p>
 * Description: [ 商品状态,1:未发布，2：待审核，20：审核驳回，3：待上架，4：在售，5：已下架，6：锁定， 7： 申请解锁]
 * </p>
 */
public enum ItemStatusEnum {

	NOT_PUBLISH(1, "未发布"), 
	AUDITING(0, "待审核"),
	SHELVING(3, "待上架"), 
	SALING(4, "在售"), 
	UNSHELVED(5, "已下架"), 
	LOCKED(6,"锁定"), 
	APPLYING(7, "申请解锁"), 
	REJECTED(2, "审核驳回"),
	DELETED(6, "已删除"),

	// add by lh 2016-11-24 start
	PASS(1, "审核通过"),
	ERP_STOCKPRICE_OR_OUTPRODUCTPRICE(3, "待ERP上行库存及价格或外部商品品类价格待映射"),
	NOT_SHELVES(4, "未上架");

	// add by lh 2016-11-24 end

	private int code;
	private String label;

	ItemStatusEnum(int code, String label) {
		this.code = code;
		this.label = label;
	}

	public static ItemStatusEnum getEnumBycode(Integer code) {
		if (code != null) {
			for (ItemStatusEnum userType : ItemStatusEnum.values()) {
				if (userType.getCode() == code) {
					return userType;
				}
			}
		}
		return null;
	}

	public int getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}
}
