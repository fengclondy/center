package cn.htd.tradecenter.dto;

import java.io.Serializable;
import java.util.List;

import cn.htd.tradecenter.common.enums.YesNoEnum;

/**
 * 订单状态统计
 * 
 * @author jiangkun
 */
public class TradeOrderCountDTO implements Serializable {

	private static final long serialVersionUID = 4988874964661301492L;

	private String orderStatus;

	private String sellerCode;

	private int cnt;

	private int isCancelOrder = YesNoEnum.NO.getValue();

	private List<String> targetOrderStatusList;

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public int getIsCancelOrder() {
		return isCancelOrder;
	}

	public void setIsCancelOrder(int isCancelOrder) {
		this.isCancelOrder = isCancelOrder;
	}

	public List<String> getTargetOrderStatusList() {
		return targetOrderStatusList;
	}

	public void setTargetOrderStatusList(List<String> targetOrderStatusList) {
		this.targetOrderStatusList = targetOrderStatusList;
	}

}
