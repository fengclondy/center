package cn.htd.tradecenter.dto.bill;

import java.io.Serializable;

public class TradeSetSellerInfoDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	
	
	
	
	
	
}
