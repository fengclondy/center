package cn.htd.zeus.tc.dto.resquest;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class OrderCreateListInfoReqDTO extends OrderCreateListInfoReqMarketDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5623303619756795745L;

	//订单号
	@NotEmpty(message = "orderNo不能为空")
	private String orderNo;
	
	//卖家编号
	@NotEmpty(message = "sellerCode不能为空")
	private String sellerCode;
	
	//店铺ID
	@NotNull(message = "shopId不能为空")
	private Long shopId;
	
	//店铺名称
	@NotEmpty(message = "shopName不能为空")
	private String shopName;
	
	//订单来源 1：商城，2：VMS开单，3：超级老板PC，4：超级老板APP
	@NotEmpty(message = "orderFrom不能为空")
	private String orderFrom;
	
	//订单备注
	private String orderRemarks;
	
	//订单行集合
	@NotNull(message = "orderItemList不能为空")
	@Valid
	private List<OrderCreateItemListInfoReqDTO> orderItemList;
	
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

	public List<OrderCreateItemListInfoReqDTO> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderCreateItemListInfoReqDTO> orderItemList) {
		this.orderItemList = orderItemList;
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
