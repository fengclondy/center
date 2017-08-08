package cn.htd.zeus.tc.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

public class OrderSellerInfoDTO implements Serializable {

	private static final long serialVersionUID = -3514597523174856538L;

	// 卖家编号
	@NotEmpty(message = "sellerCode不能为空")
	private String sellerCode;

	// 店铺ID
	private Long shopId;

	// 卖家ID
	private long sellerId;

	// 店铺名称
	@NotEmpty(message = "shopName不能为空")
	private String shopName;

	// 商品sku信息
	@NotEmpty(message = "skuInfoList不能为空")
	@Valid
	public List<OrderSkuInfoDTO> skuInfoList = new ArrayList<OrderSkuInfoDTO>();

	/**
	 * @return the sellerCode
	 */
	public String getSellerCode() {
		return sellerCode;
	}

	/**
	 * @param sellerCode
	 *            the sellerCode to set
	 */
	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	/**
	 * @return the shopId
	 */
	public Long getShopId() {
		return shopId;
	}

	/**
	 * @param shopId
	 *            the shopId to set
	 */
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	/**
	 * @return the sellerId
	 */
	public long getSellerId() {
		return sellerId;
	}

	/**
	 * @param sellerId
	 *            the sellerId to set
	 */
	public void setSellerId(long sellerId) {
		this.sellerId = sellerId;
	}

	/**
	 * @return the shopName
	 */
	public String getShopName() {
		return shopName;
	}

	/**
	 * @param shopName
	 *            the shopName to set
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	/**
	 * @return the skuInfoList
	 */
	public List<OrderSkuInfoDTO> getSkuInfoList() {
		return skuInfoList;
	}

	/**
	 * @param skuInfoList
	 *            the skuInfoList to set
	 */
	public void setSkuInfoList(List<OrderSkuInfoDTO> skuInfoList) {
		this.skuInfoList = skuInfoList;
	}

}
