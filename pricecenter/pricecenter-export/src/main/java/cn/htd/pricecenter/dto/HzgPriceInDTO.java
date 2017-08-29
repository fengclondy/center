package cn.htd.pricecenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class HzgPriceInDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8568695826686861388L;
	private Long itemId;
	private Long skuId;
	private Long shopId;
	//建议零售价
	private BigDecimal retailPrice;
	//销售价
	private BigDecimal salePrice;
	//vip等级价格
	private BigDecimal vipPrice;
	//操作人id
	private Long operatorId;
	//操作人名称
	private String operatorName;
	//大B的Id
	private Long sellerId;
	public BigDecimal getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}
	public BigDecimal getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
	public BigDecimal getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(BigDecimal vipPrice) {
		this.vipPrice = vipPrice;
	}
	public Long getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

}
