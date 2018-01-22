/**
 * Copyright (C), 2013-2016, 汇通达网络有限公司
 * FileName: 	VmsCreateTradeOrderItemInDTO.java
 * Author:   	jiangkun
 * Date:     	2016年11月30日
 * Description: VMS新增订单行信息DTO  
 * History: 	
 * <author>		<time>      	<version>	<desc>
 * jiangkun		2016年11月30日	1.0			创建
 */
package cn.htd.tradecenter.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 * VMS新增订单行IN DTO
 * 
 * @author jiangkun
 *
 */
public class VenusCreateTradeOrderItemDTO extends VenusConfirmTradeOrderItemWarehouseDTO implements Serializable {

	private static final long serialVersionUID = 3830213455225621918L;

	/**
	 * 商品SKU编码
	 */
	@NotBlank(message = "商品SKU编码不能为空")
	private String skuCode;

	/**
     * 商品SPU编码
     */
    @NotBlank(message = "商品SPU编码不能为空")
    private String spuCode;

    /**
	 * 包厢商品 0:大厅, 1:包厢
	 */
	@NotNull(message = "包厢商品标志为空")
	@Range(min = 0, max = 1, message = "包厢商品标志为非法值")
	private int isBoxFlag;

	/**
	 * 商品分销限价
	 */
	@NotNull(message = "商品分销限价不能为空")
	@DecimalMin("0")
	private BigDecimal costPrice;

	/**
	 * 商品单价
	 */
	@NotNull(message = "商品单价不能为空")
	@DecimalMin("0")
	private BigDecimal goodsPrice;

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

    public String getSpuCode() {
        return spuCode;
    }

    public void setSpuCode(String spuCode) {
        this.spuCode = spuCode;
    }

	public int getIsBoxFlag() {
		return isBoxFlag;
	}

	public void setIsBoxFlag(int isBoxFlag) {
		this.isBoxFlag = isBoxFlag;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

}
