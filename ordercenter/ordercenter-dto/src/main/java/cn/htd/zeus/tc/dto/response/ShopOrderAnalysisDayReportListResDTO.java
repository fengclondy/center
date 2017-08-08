/**
 * 
 */
package cn.htd.zeus.tc.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ly
 *
 */
public class ShopOrderAnalysisDayReportListResDTO extends GenricResDTO implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5017993915251011175L;

	private Integer salesTime;
    
    private BigDecimal salesAmount;

    private Integer salesGoodsCount;

    private Integer orderGoodsCount;
    
    private String goodsName;

    private String itemCode;
    
    private String skuCode;

	public Integer getSalesTime() {
		return salesTime;
	}

	public void setSalesTime(Integer salesTime) {
		this.salesTime = salesTime;
	}

	public BigDecimal getSalesAmount() {
		return salesAmount;
	}

	public void setSalesAmount(BigDecimal salesAmount) {
		this.salesAmount = salesAmount;
	}

	public Integer getSalesGoodsCount() {
		return salesGoodsCount;
	}

	public void setSalesGoodsCount(Integer salesGoodsCount) {
		this.salesGoodsCount = salesGoodsCount;
	}

	public Integer getOrderGoodsCount() {
		return orderGoodsCount;
	}

	public void setOrderGoodsCount(Integer orderGoodsCount) {
		this.orderGoodsCount = orderGoodsCount;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
    
}
