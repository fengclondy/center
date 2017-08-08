package cn.htd.goodscenter.dto.indto;

import java.io.Serializable;

public class PauseItemInDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2134182414665243505L;
	//必填 店铺id
	private Long shopId;
	//必填 供应商id
	private Long sellerId;
	//必填 操作者id
	private Long operatorId;
	//必填 操作者名称
	private String operatorName;
	//非必填
	private String statusChangeReason;
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
	public String getStatusChangeReason() {
		return statusChangeReason;
	}
	public void setStatusChangeReason(String statusChangeReason) {
		this.statusChangeReason = statusChangeReason;
	}
	
	
}
