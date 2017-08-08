package cn.htd.marketcenter.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 优惠规则校验用对象
 */
public class BuyerInfoDTO implements Serializable {

	private static final long serialVersionUID = -8054312634722254223L;
	/**
	 * 买家编码
	 */
	@NotBlank(message = "会员编码不能为空")
	private String buyerCode;
	/**
	 * 卖家编码
	 */
	@NotBlank(message = "卖家编码不能为空")
	private String sellerCode;

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}
}