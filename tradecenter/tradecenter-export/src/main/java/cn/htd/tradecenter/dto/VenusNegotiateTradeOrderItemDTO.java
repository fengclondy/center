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

/**
 * VMS新增订单行DTO
 * 
 * @author jiangkun
 *
 */
public class VenusNegotiateTradeOrderItemDTO implements Serializable {

	private static final long serialVersionUID = 3830213455225621918L;

	/**
	 * 订单行编号
	 */
	private String orderItemNo;
	/**
	 * 商品SKUID
	 */
	private String skuCode;
	/**
	 * 包厢商品 0:大厅, 1:包厢
	 */
	private int isBoxFlag;
	/**
	 * 订单行modifyTime做乐观排他用
	 */
	private String modifyTimeStr;
	/**
	 * 分销限价
	 */
	private BigDecimal costPrice;
	/**
	 * 议价数量
	 */
	private Integer bargainingGoodsCount;
	/**
	 * 议价单价
	 */
	private BigDecimal bargainingGoodsPrice;

	public String getOrderItemNo() {
		return orderItemNo;
	}

	public void setOrderItemNo(String orderItemNo) {
		this.orderItemNo = orderItemNo;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public int getIsBoxFlag() {
		return isBoxFlag;
	}

	public void setIsBoxFlag(int isBoxFlag) {
		this.isBoxFlag = isBoxFlag;
	}

	public String getModifyTimeStr() {
		return modifyTimeStr;
	}

	public void setModifyTimeStr(String modifyTimeStr) {
		this.modifyTimeStr = modifyTimeStr;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public Integer getBargainingGoodsCount() {
		return bargainingGoodsCount;
	}

	public void setBargainingGoodsCount(Integer bargainingGoodsCount) {
		this.bargainingGoodsCount = bargainingGoodsCount;
	}

	public BigDecimal getBargainingGoodsPrice() {
		return bargainingGoodsPrice;
	}

	public void setBargainingGoodsPrice(BigDecimal bargainingGoodsPrice) {
		this.bargainingGoodsPrice = bargainingGoodsPrice;
	}

}
