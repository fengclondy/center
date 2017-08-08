package cn.htd.zeus.tc.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderCreateListInfoResDTO  extends GenricResDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5416651120086534090L;
	
	//订单号
    private String orderNo;
    
    //秒杀订单截止时间
    private Date payTimeLimit;
		
	//订单行List
	private List<OrderCreateItemListInfoResDTO> orderItemResList;
	
	//卖家编号
	private String sellerUserId;
	
	//交易金额
	private BigDecimal amount;
	
	//买家id
	private String buyerUserId;
		
	//付款人UserId
	private String payerUserId;
	
	//支付场景  1-可走所有支付场景   0-不能走erp余额支付场景
    private String tradeScene;
	
    private String channelCode;
    
	private List<ChargeConditionInfoDTO> chargeConditionInfo;
	
	//店铺名称提供给交易名称
	private String shopName;
	
	//许雪焦专用，我是被迫加的，以后不要找张丁
	private String orderStatus;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public List<OrderCreateItemListInfoResDTO> getOrderItemResList() {
		return orderItemResList;
	}

	public void setOrderItemResList(
			List<OrderCreateItemListInfoResDTO> orderItemResList) {
		this.orderItemResList = orderItemResList;
	}

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

	public List<ChargeConditionInfoDTO> getChargeConditionInfo() {
		return chargeConditionInfo;
	}

	public void setChargeConditionInfo(
			List<ChargeConditionInfoDTO> chargeConditionInfo) {
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

	public String getTradeScene() {
		return tradeScene;
	}

	public void setTradeScene(String tradeScene) {
		this.tradeScene = tradeScene;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public Date getPayTimeLimit() {
		return payTimeLimit;
	}

	public void setPayTimeLimit(Date payTimeLimit) {
		this.payTimeLimit = payTimeLimit;
	}
	
}
