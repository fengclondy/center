package cn.htd.pricecenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import cn.htd.pricecenter.domain.ItemSkuLadderPrice;

public class CommonItemSkuPriceDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -199103879331843153L;
	//外部供应商的阶梯价格
	private List<ItemSkuLadderPrice> ladderPriceList;
	//非外部供应商的价格
	private BigDecimal salesPrice;
	//商品单价种类 0 销售价 1 阶梯价 2 分组价 3 等级价 4 区域价 
	private Integer goodsPriceType;
	
	public List<ItemSkuLadderPrice> getLadderPriceList() {
		return ladderPriceList;
	}
	public void setLadderPriceList(List<ItemSkuLadderPrice> ladderPriceList) {
		this.ladderPriceList = ladderPriceList;
	}
	public BigDecimal getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(BigDecimal salesPrice) {
		this.salesPrice = salesPrice;
	}
	public Integer getGoodsPriceType() {
		return goodsPriceType;
	}
	public void setGoodsPriceType(Integer goodsPriceType) {
		this.goodsPriceType = goodsPriceType;
	}
	
}
