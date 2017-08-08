package cn.htd.marketcenter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class OrderInfoDTO implements Serializable {
	private static final long serialVersionUID = -3449192420335972461L;

	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 卖家编码
	 */
	@NotBlank(message = "卖家编码不能为空")
	private String sellerCode;
	/**
	 * 卖家类型
	 */
	private String sellerType;
	/**
	 * 卖家名称
	 */
	private String sellerName;
	/**
	 * 店铺ID
	 */
	private Long shopId;
	/**
	 * 店铺名称
	 */
	private String shopName;
	/**
	 * 订单来源 1：商城，2：VMS开单，3：超级老板PC，4：超级老板APP
	 */
	private String orderFrom;
	/**
	 * 订单备注
	 */
	private String orderRemarks;
	/**
	 * 订单支付限制时间
	 */
	private Date payTimeLimit;
	/**
	 * 是否有外接渠道商品
	 */
	private Integer hasProductplusFlag;
	/**
	 * 是否使用了优惠券标记
	 */
	private boolean hasUsedCoupon = false;
	/**
	 * 是否是秒杀订单
	 */
	private boolean isTimelimitedOrder = false;
	/**
	 * 商品列表
	 */
	@NotNull(message = "购物车商品不能为空")
	@Valid
	private List<OrderItemInfoDTO> orderItemList;
	/**
	 * 订单扩展字段列表
	 */
	private Map<String, Object> extendMap;

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

	public Date getPayTimeLimit() {
		return payTimeLimit;
	}

	public void setPayTimeLimit(Date payTimeLimit) {
		this.payTimeLimit = payTimeLimit;
	}

	public boolean isHasUsedCoupon() {
		return hasUsedCoupon;
	}

	public void setHasUsedCoupon(boolean hasUsedCoupon) {
		this.hasUsedCoupon = hasUsedCoupon;
	}

	public boolean isTimelimitedOrder() {
		return isTimelimitedOrder;
	}

	public void setTimelimitedOrder(boolean isTimelimitedOrder) {
		this.isTimelimitedOrder = isTimelimitedOrder;
	}

	public Integer getHasProductplusFlag() {
		return hasProductplusFlag;
	}

	public void setHasProductplusFlag(Integer hasProductplusFlag) {
		this.hasProductplusFlag = hasProductplusFlag;
	}

	public List<OrderItemInfoDTO> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItemInfoDTO> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public Map<String, Object> getExtendMap() {
		return extendMap;
	}

	public void setExtendMap(Map<String, Object> extendMap) {
		this.extendMap = extendMap;
	}

	public void initBeforeCalculateCoupon() {
		this.hasUsedCoupon = false;
		if (this.orderItemList != null && !orderItemList.isEmpty()) {
			for (OrderItemInfoDTO orderItemDTO : orderItemList) {
				orderItemDTO.initBeforeCalculateCoupon(this.sellerCode);
			}
		}
	}

}
