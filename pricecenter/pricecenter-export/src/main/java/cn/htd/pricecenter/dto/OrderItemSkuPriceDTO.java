package cn.htd.pricecenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import cn.htd.pricecenter.domain.ItemSkuLadderPrice;

public class OrderItemSkuPriceDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3632515292611523570L;
	
	//外部供货价/分销限价
	private BigDecimal costPrice;
	//价格浮动比例
	private BigDecimal priceFloatingRatio;
	//佣金比例
	private BigDecimal commissionRatio;
	//销售单价
	private BigDecimal salePrice;
	//商品单价种类 0 销售价 1 阶梯价 2 分组价 3 等级价 4 区域价 
	private Integer goodsPriceType;
	//	商品单价种类对应信息
	//商品单价种类对应信息(商品单价种类为销售价时为空，阶梯价时为1-10阶梯上限-阶梯下限，区域价时为区域编码，会员分组价时为会员分组ID，会员等级价时为会员等级编码）
	private String goodsPriceInfo;
	//商品单价
	private BigDecimal goodsPrice;
	//外部供应商的阶梯价格
	private List<ItemSkuLadderPrice> ladderPriceList;
	//汇掌柜价格
	private HzgPriceDTO hzgPrice;
	
	public BigDecimal getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}
	public BigDecimal getPriceFloatingRatio() {
		return priceFloatingRatio;
	}
	public void setPriceFloatingRatio(BigDecimal priceFloatingRatio) {
		this.priceFloatingRatio = priceFloatingRatio;
	}
	public BigDecimal getCommissionRatio() {
		return commissionRatio;
	}
	public void setCommissionRatio(BigDecimal commissionRatio) {
		this.commissionRatio = commissionRatio;
	}
	public BigDecimal getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
	public Integer getGoodsPriceType() {
		return goodsPriceType;
	}
	public void setGoodsPriceType(Integer goodsPriceType) {
		this.goodsPriceType = goodsPriceType;
	}
	public String getGoodsPriceInfo() {
		return goodsPriceInfo;
	}
	public void setGoodsPriceInfo(String goodsPriceInfo) {
		this.goodsPriceInfo = goodsPriceInfo;
	}
	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public List<ItemSkuLadderPrice> getLadderPriceList() {
		return ladderPriceList;
	}
	public void setLadderPriceList(List<ItemSkuLadderPrice> ladderPriceList) {
		this.ladderPriceList = ladderPriceList;
	}
	public HzgPriceDTO getHzgPrice() {
		return hzgPrice;
	}
	public void setHzgPrice(HzgPriceDTO hzgPrice) {
		this.hzgPrice = hzgPrice;
	}
	
}
