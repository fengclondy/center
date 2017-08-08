package cn.htd.marketcenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderItemCouponDTO extends BuyerCouponInfoDTO implements Serializable {

	private static final long serialVersionUID = -1896172897174859290L;

	/**
	 * 优惠券适用的商品
	 */
	private List<OrderItemInfoDTO> productList = new ArrayList<OrderItemInfoDTO>();
	/**
	 * 该优惠券适用商品总金额
	 */
	private BigDecimal itemTotalAmount = BigDecimal.ZERO;
	/**
	 * 该优惠券折扣总金额
	 */
	private BigDecimal totalDiscountAmount = BigDecimal.ZERO;
	/**
	 * 该优惠券适用商品分摊的优惠金额
	 */
	private BigDecimal sharedDiscountAmount = BigDecimal.ZERO;
	/**
	 * 错误信息
	 */
	private String errorMsg = "";

	public List<OrderItemInfoDTO> getProductList() {
		return productList;
	}

	public void setProductList(List<OrderItemInfoDTO> productList) {
		this.productList = productList;
	}

	public BigDecimal getItemTotalAmount() {
		return itemTotalAmount;
	}

	public void setItemTotalAmount(BigDecimal itemTotalAmount) {
		this.itemTotalAmount = itemTotalAmount;
	}

	public BigDecimal getTotalDiscountAmount() {
		return totalDiscountAmount;
	}

	public void setTotalDiscountAmount(BigDecimal totalDiscountAmount) {
		this.totalDiscountAmount = totalDiscountAmount;
	}

	public BigDecimal getSharedDiscountAmount() {
		return sharedDiscountAmount;
	}

	public void setSharedDiscountAmount(BigDecimal sharedDiscountAmount) {
		this.sharedDiscountAmount = sharedDiscountAmount;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
