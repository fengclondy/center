package cn.htd.zeus.tc.biz.dmo;

import java.math.BigDecimal;
import java.util.List;

/*
 * 支付对象
 */
public class OrderCreateListInfoPayDMO {

	//卖家编号
	private String sellerUserId;
	
	//交易金额
	private BigDecimal amount;
	
	//买家id
	private String buyerUserId;
	
	//付款人UserId
	private String payerUserId;
	
	//店铺名称 - 提供给交易名称
	private String shopName;
	
	//支付场景  1-可走所有支付场景   0-不能走erp余额支付场景
	private String tradeScene;
	
	private String channelCode;
	
	private List<ChargeConditionInfoDMO> chargeConditionInfo;

	public String getSellerUserId() {
		return sellerUserId;
	}

	public void setSellerUserId(String sellerUserId) {
		this.sellerUserId = sellerUserId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public List<ChargeConditionInfoDMO> getChargeConditionInfo() {
		return chargeConditionInfo;
	}

	public void setChargeConditionInfo(List<ChargeConditionInfoDMO> chargeConditionInfo) {
		this.chargeConditionInfo = chargeConditionInfo;
	}

	public String getBuyerUserId() {
		return buyerUserId;
	}

	public void setBuyerUserId(String buyerUserId) {
		this.buyerUserId = buyerUserId;
	}

	public String getPayerUserId() {
		return payerUserId;
	}

	public void setPayerUserId(String payerUserId) {
		this.payerUserId = payerUserId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getTradeScene() {
		return tradeScene;
	}

	public void setTradeScene(String tradeScene) {
		this.tradeScene = tradeScene;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
}
