package cn.htd.zeus.tc.biz.dmo;

import java.util.List;

import cn.htd.zeus.tc.dto.OrderItemPaymentDTO;

public class OrderPayInfoDMO extends GenericDMO {

	/**
	 *
	 */
	private static final long serialVersionUID = -3637806351297129541L;

	/**
	 * 清分状态
	 */
	private String distributionStatus;

	/**
	 * 订单号
	 */
	private String merchantOrderNo;

	/**
	 * 支付状态
	 */
	private String tradeStatus;

	/**
	 * 支付方式
	 */
	private String tradeType;

	/**
	 * 支付金额
	 */
	private String amount;

	/**
	 * 是否更新佣金标志 0：非外部供应商 1：外部供应商
	 */
	private String flag;

	/**
	 * 订单行佣金
	 */
	private List<OrderItemPaymentDTO> commissionList;

	public String getDistributionStatus() {
		return distributionStatus;
	}

	public void setDistributionStatus(String distributionStatus) {
		this.distributionStatus = distributionStatus;
	}

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param flag
	 *            the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * @return the commissionList
	 */
	public List<OrderItemPaymentDTO> getCommissionList() {
		return commissionList;
	}

	/**
	 * @param commissionList
	 *            the commissionList to set
	 */
	public void setCommissionList(List<OrderItemPaymentDTO> commissionList) {
		this.commissionList = commissionList;
	}

}
