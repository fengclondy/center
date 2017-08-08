package cn.htd.goodscenter.dto.venus.outdto;

import java.io.Serializable;
import java.math.BigDecimal;

public class VenusDropdownItemObjDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8278179758316490020L;
	private BigDecimal salesLimitPrice;
	//销售价格
	private BigDecimal salesPrice;
	//显示库存
	private String displayQty;
	//锁定库存
	private String reserveQuantity;
	//包厢类型：1 包厢类型 2 区域类型
	private String shelfType;
	//上架标记：1 已上架 0 未上架
	private String isVisible;
	
	public BigDecimal getSalesLimitPrice() {
		return salesLimitPrice;
	}
	public void setSalesLimitPrice(BigDecimal salesLimitPrice) {
		this.salesLimitPrice = salesLimitPrice;
	}
	public BigDecimal getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(BigDecimal salesPrice) {
		this.salesPrice = salesPrice;
	}
	public String getDisplayQty() {
		return displayQty;
	}
	public void setDisplayQty(String displayQty) {
		this.displayQty = displayQty;
	}
	public String getShelfType() {
		return shelfType;
	}
	public void setShelfType(String shelfType) {
		this.shelfType = shelfType;
	}
	public String getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(String isVisible) {
		this.isVisible = isVisible;
	}
	public String getReserveQuantity() {
		return reserveQuantity;
	}
	public void setReserveQuantity(String reserveQuantity) {
		this.reserveQuantity = reserveQuantity;
	}
	

}
