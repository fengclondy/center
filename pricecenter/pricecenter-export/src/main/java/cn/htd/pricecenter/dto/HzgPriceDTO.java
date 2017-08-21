package cn.htd.pricecenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 汇掌柜价格
 * 
 * @author zhangxiaolong
 *
 */
public class HzgPriceDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9003154209085653946L;
	
	//建议零售价
	private BigDecimal retailPrice;
	//销售价
	private BigDecimal salePrice;
	//vip等级价格
	private BigDecimal vipPrice;
	public BigDecimal getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}
	public BigDecimal getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
	public BigDecimal getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(BigDecimal vipPrice) {
		this.vipPrice = vipPrice;
	}
	
}
