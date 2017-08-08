package cn.htd.zeus.tc.dto.response;

import java.io.Serializable;
import java.util.List;

public class OrderCreateListInfoMarketResDTO extends GenricResDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7163055266283436759L;
	
	//订单号
	private String orderNo;
	
	//卖家编号
	private String sellerCode;
	
	//店铺ID
    private Long shopId;
		
	//店铺名称
	private String shopName;
	
	//订单来源 1：商城，2：VMS开单，3：超级老板PC，4：超级老板APP
	private String orderFrom;
	
	//订单备注
	private String orderRemarks;
	
	//订单行集合
	private List<OrderCreateItemListInfoMarketResDTO> orderItemList;
	
	//是否有外接渠道商品
	private Integer hasProductplusFlag;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getOrderFrom() {
		return orderFrom;
	}

	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
	}

	public String getOrderRemarks() {
		return orderRemarks;
	}

	public void setOrderRemarks(String orderRemarks) {
		this.orderRemarks = orderRemarks;
	}

	public List<OrderCreateItemListInfoMarketResDTO> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderCreateItemListInfoMarketResDTO> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public Integer getHasProductplusFlag() {
		return hasProductplusFlag;
	}

	public void setHasProductplusFlag(Integer hasProductplusFlag) {
		this.hasProductplusFlag = hasProductplusFlag;
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
}
