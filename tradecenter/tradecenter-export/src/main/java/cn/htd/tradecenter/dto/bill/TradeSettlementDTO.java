package cn.htd.tradecenter.dto.bill;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TradeSettlementDTO implements Serializable{

	private static final long serialVersionUID = -1920705358735116179L;
	
	private Long id ;
	//结算单号
	private String settlementNo ;
	//卖家ID
	private Long sellerId ;
	//卖家编号
	private String sellerCode ;
	//卖家类型 1:是内部，2是外部。
	private String sellerType ;
	//卖家名称
	private String sellerName ;
	//店铺ID
	private Long shopId ;
	//店铺名称
	private String shopName ;
	//渠道编码: 10 内部供应商 20 外部供应商 3010 京东商品＋
	private String productChannelCode ;
	//渠道名称
	private String productChannelName ;
	//结算状态:10(待财务确认)、11(待商家提款)、12(商家提款处理中)、13(结算已完成)、14(结算单失效)
	private String status;
	//结算单生成时间(job的生成时间)
	private Date settlementTime;
	//佣金总金额
	private BigDecimal totalCommissionAmount;
	//结算总金额
	private BigDecimal totalSettlementAmount;
	
	
	private Long createId;
	private String createName;
	private Date createTime;
	private Long modifyId;
	private String modifyName;
	private Date modifyTime;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSettlementNo() {
		return settlementNo;
	}
	public void setSettlementNo(String settlementNo) {
		this.settlementNo = settlementNo;
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
	public String getProductChannelCode() {
		return productChannelCode;
	}
	public void setProductChannelCode(String productChannelCode) {
		this.productChannelCode = productChannelCode;
	}
	public String getProductChannelName() {
		return productChannelName;
	}
	public void setProductChannelName(String productChannelName) {
		this.productChannelName = productChannelName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public BigDecimal getTotalCommissionAmount() {
		return totalCommissionAmount;
	}
	public void setTotalCommissionAmount(BigDecimal totalCommissionAmount) {
		this.totalCommissionAmount = totalCommissionAmount;
	}
	public BigDecimal getTotalSettlementAmount() {
		return totalSettlementAmount;
	}
	public void setTotalSettlementAmount(BigDecimal totalSettlementAmount) {
		this.totalSettlementAmount = totalSettlementAmount;
	}
	public Date getSettlementTime() {
		return settlementTime;
	}
	public void setSettlementTime(Date settlementTime) {
		this.settlementTime = settlementTime;
	}
	
	
	
}
