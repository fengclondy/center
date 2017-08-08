package cn.htd.tradecenter.domain;

import java.util.List;

/**
 * 订单结算统计
 * 
 * @author zf.zhang
 */
public class TradeSettlementCount {

	private String orderStatus;

	private Long sellerId;

	// 每页记录数
	private int cnt;

	private List<String> targetOrderStatusList;

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public List<String> getTargetOrderStatusList() {
		return targetOrderStatusList;
	}

	public void setTargetOrderStatusList(List<String> targetOrderStatusList) {
		this.targetOrderStatusList = targetOrderStatusList;
	}

}
