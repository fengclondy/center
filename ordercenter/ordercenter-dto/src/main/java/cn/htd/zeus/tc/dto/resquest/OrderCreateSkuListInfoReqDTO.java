package cn.htd.zeus.tc.dto.resquest;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class OrderCreateSkuListInfoReqDTO implements Serializable {

	private static final long serialVersionUID = -7082845410146900648L;

	// 商品SKU编码
	@NotBlank(message = "skuCode不能为空")
	private String skuCode;

	// 商品数量
	@NotNull(message = "goodsCount不能为空")
	private Long goodsCount;

	// 渠道编码
	private String channelCode;

	// 是否包厢标志 0：否，1：是
	@NotNull(message = "isBoxFlag不能为空")
	private Integer isBoxFlag;

	/**
	 * @return the skuCode
	 */
	public String getSkuCode() {
		return skuCode;
	}

	/**
	 * @param skuCode
	 *            the skuCode to set
	 */
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	/**
	 * @return the goodsCount
	 */
	public Long getGoodsCount() {
		return goodsCount;
	}

	/**
	 * @param goodsCount
	 *            the goodsCount to set
	 */
	public void setGoodsCount(Long goodsCount) {
		this.goodsCount = goodsCount;
	}

	/**
	 * @return the channelCode
	 */
	public String getChannelCode() {
		return channelCode;
	}

	/**
	 * @param channelCode
	 *            the channelCode to set
	 */
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	/**
	 * @return the isBoxFlag
	 */
	public Integer getIsBoxFlag() {
		return isBoxFlag;
	}

	/**
	 * @param isBoxFlag
	 *            the isBoxFlag to set
	 */
	public void setIsBoxFlag(Integer isBoxFlag) {
		this.isBoxFlag = isBoxFlag;
	}

}
