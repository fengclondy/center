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

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
 * 订单行拆单信息DTO
 * 
 * @author jiangkun
 *
 */
public class VenusConfirmTradeOrderItemWarehouseDTO implements Serializable {

	private static final long serialVersionUID = 3830213455225621918L;

	/**
	 * 订单行拆单明细信息ID
	 */
	private Long id;

	/**
	 * 商品数量
	 */
	@NotNull(message = "商品数量不能为空")
	@Range(min = 1, message = "商品数量必须大于0")
	private int goodsCount;

	/**
	 * 仓库代码
	 */
	@NotBlank(message = "仓库代码不能为空")
	@Length(max = 32, message = "仓库代码超长")
	private String warehouseCode;

	/**
	 * 仓库名称
	 */
	@NotBlank(message = "仓库名称不能为空")
	@Length(max = 255, message = "仓库名称超长")
	private String warehouseName;

	/**
	 * 供货商编码
	 */
	@NotBlank(message = "供货商编码不能为空")
	@Length(max = 32, message = "供货商编码超长")
	private String supplierCode;

	/**
	 * 供货商名称
	 */
	@NotBlank(message = "供货商名称不能为空")
	@Length(max = 255, message = "供货商名称超长")
	private String supplierName;

	/**
	 * 采购部门编码
	 */
	@NotBlank(message = "采购部门编码不能为空")
	@Length(max = 32, message = "采购部门编码超长")
	private String purchaseDepartmentCode;

	/**
	 * 采购部门名称
	 */
	@NotBlank(message = "采购部门名称不能为空")
	@Length(max = 255, message = "采购部门名称超长")
	private String purchaseDepartmentName;

	/**
	 * 商品属性
	 */
	@NotEmpty(message = "商品属性不能为空")
	private String productAttribute;

	/**
	 * 可用库存
	 */
	@NotNull(message = "可用库存不能为空")
	@Range(min = 1, message = "可用库存必须大于0")
	private int availableInventory;

	/**
	 * 使用返利金额
	 */
	@DecimalMin("0")
	private BigDecimal rebateUsedAmount = BigDecimal.ZERO;

	/**
	 * 是否是协议商品
	 */
	@Range(min = 0, max = 1, message = "协议商品标志不正确")
	private int isAgreementSku;

	/**
	 * 协议编号
	 */
	@Length(max = 64, message = "协议编号超长")
	private String agreementCode = "";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(int goodsCount) {
		this.goodsCount = goodsCount;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getPurchaseDepartmentCode() {
		return purchaseDepartmentCode;
	}

	public void setPurchaseDepartmentCode(String purchaseDepartmentCode) {
		this.purchaseDepartmentCode = purchaseDepartmentCode;
	}

	public String getPurchaseDepartmentName() {
		return purchaseDepartmentName;
	}

	public void setPurchaseDepartmentName(String purchaseDepartmentName) {
		this.purchaseDepartmentName = purchaseDepartmentName;
	}

	public String getProductAttribute() {
		return productAttribute;
	}

	public void setProductAttribute(String productAttribute) {
		this.productAttribute = productAttribute;
	}

	public int getAvailableInventory() {
		return availableInventory;
	}

	public void setAvailableInventory(int availableInventory) {
		this.availableInventory = availableInventory;
	}

	public BigDecimal getRebateUsedAmount() {
		return rebateUsedAmount;
	}

	public void setRebateUsedAmount(BigDecimal rebateUsedAmount) {
		this.rebateUsedAmount = rebateUsedAmount;
	}

	public int getIsAgreementSku() {
		return isAgreementSku;
	}

	public void setIsAgreementSku(int isAgreementSku) {
		this.isAgreementSku = isAgreementSku;
	}

	public String getAgreementCode() {
		return agreementCode;
	}

	public void setAgreementCode(String agreementCode) {
		this.agreementCode = agreementCode;
	}

}
