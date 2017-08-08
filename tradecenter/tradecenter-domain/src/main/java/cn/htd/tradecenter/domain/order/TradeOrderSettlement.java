package cn.htd.tradecenter.domain.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TradeOrderSettlement implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Long id;

	private String tradeNo;

	private String orderNo;

	private Long buyerId;

	private String buyerCode;

	private String buyerType;

	private String buyerName;

	private Long sellerId;

	private String sellerCode;

	private String sellerErpCode;

	private String sellerType;

	private String sellerName;

	private Long shopId;

	private String shopName;

	//是否有外接渠道商品
	private int hasProductplusFlag;
	
	//是否是VIP会员（0.不是，1.是）
	private int isVipMember;
    //订单商品总数量
	private Integer totalGoodsCount;
    //订单商品总金额   所有订单行的商品总金额合计
	private BigDecimal totalGoodsAmount;
    //运费总金额
	private BigDecimal totalFreight;
    //用券优惠总金额   订单分担优惠券总金额
	private BigDecimal totalDiscountAmount;
	
	//订单状态
	private String orderStatus;
	
	//支付方式：1：余额帐支付，2：平台账户支付，3：在线支付
	private String payType;

	private Long createId;

	private String createName;

	private Date createTime;

	private Long modifyId;

	private String modifyName;

	private Date modifyTime;

	private List<TradeOrderSettlementItems> orderItemList;

	private List<TradeOrderSettlementStatusHistory> orderStatusHistoryDTOList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	public String getBuyerType() {
		return buyerType;
	}

	public void setBuyerType(String buyerType) {
		this.buyerType = buyerType;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getSellerErpCode() {
		return sellerErpCode;
	}

	public void setSellerErpCode(String sellerErpCode) {
		this.sellerErpCode = sellerErpCode;
	}

	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public int getHasProductplusFlag() {
		return hasProductplusFlag;
	}

	public void setHasProductplusFlag(int hasProductplusFlag) {
		this.hasProductplusFlag = hasProductplusFlag;
	}

	public Integer getTotalGoodsCount() {
		return totalGoodsCount;
	}

	public void setTotalGoodsCount(Integer totalGoodsCount) {
		this.totalGoodsCount = totalGoodsCount;
	}

	public BigDecimal getTotalGoodsAmount() {
		return totalGoodsAmount;
	}

	public void setTotalGoodsAmount(BigDecimal totalGoodsAmount) {
		this.totalGoodsAmount = totalGoodsAmount;
	}

	public BigDecimal getTotalFreight() {
		return totalFreight;
	}

	public void setTotalFreight(BigDecimal totalFreight) {
		this.totalFreight = totalFreight;
	}

	public BigDecimal getTotalDiscountAmount() {
		return totalDiscountAmount;
	}

	public void setTotalDiscountAmount(BigDecimal totalDiscountAmount) {
		this.totalDiscountAmount = totalDiscountAmount;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getModifyId() {
		return modifyId;
	}

	public void setModifyId(Long modifyId) {
		this.modifyId = modifyId;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public List<TradeOrderSettlementItems> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<TradeOrderSettlementItems> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public List<TradeOrderSettlementStatusHistory> getOrderStatusHistoryDTOList() {
		return orderStatusHistoryDTOList;
	}

	public void setOrderStatusHistoryDTOList(
			List<TradeOrderSettlementStatusHistory> orderStatusHistoryDTOList) {
		this.orderStatusHistoryDTOList = orderStatusHistoryDTOList;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public int getIsVipMember() {
		return isVipMember;
	}

	public void setIsVipMember(int isVipMember) {
		this.isVipMember = isVipMember;
	}


	
	
	


	
	
}