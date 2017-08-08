package cn.htd.goodscenter.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class ItemArrivalNotifyDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4435027550268144960L;
	//买家id
	@NotNull(message="buyerId 不能为空")
    private Long buyerId;
    //供应商id
	@NotNull(message="sellerId 不能为空")
    private Long sellerId;
    //店铺id
	@NotNull(message="shopId 不能为空")
    private Long shopId;
    //商品id
	@NotNull(message="itemId 不能为空")
    private Long itemId;
    //商品skuid
	@NotNull(message="skuId 不能为空")
    private Long skuId;
    //手机号码
	@NotNull(message="notifyMobile 不能为空")
    private Long notifyMobile;
    //是否包厢
	@NotNull(message="isBoxFlag 不能为空")
    private String isBoxFlag;
	@NotNull(message="operatorId 不能为空")
	private Long operatorId;
	@NotEmpty(message="operatorName 不能为空")
	private String operatorName;
	
	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
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

	public Long getNotifyMobile() {
		return notifyMobile;
	}

	public void setNotifyMobile(Long notifyMobile) {
		this.notifyMobile = notifyMobile;
	}

	public String getIsBoxFlag() {
		return isBoxFlag;
	}

	public void setIsBoxFlag(String isBoxFlag) {
		this.isBoxFlag = isBoxFlag;
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
    
    

}
