package cn.htd.goodscenter.dto.venus.indto;

import java.io.Serializable;

import cn.htd.goodscenter.dto.common.AbstractPagerDTO;
/**
 * 
 * @author zhangxiaolong
 *
 */
public class VenusItemSkuPublishInfoInDTO extends AbstractPagerDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5885632161994244535L;
	//
	private Long sellerId;
	private String productCode;
	private String productName;
	private String categoryName;
	private String brandName;
	/**
	 * 库存
	 * 0 全部
	 * 1 有货
	 * 2 无货
	 */
	private String stockLevel;
	/**
	 * 上架状态
	 * 0 全部
	 * 1 未上架
	 * 2 已上架
	 */
	private String shelfStatus;
	/**
	 * 上架类型
	 * 1 包厢
	 * 2 区域
	 */
	private String shelfType;
	/**
	 * 供应商编码
	 * 
	 */
	private String supplierCode;
	
	
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getStockLevel() {
		return stockLevel;
	}
	public void setStockLevel(String stockLevel) {
		this.stockLevel = stockLevel;
	}
	public String getShelfStatus() {
		return shelfStatus;
	}
	public void setShelfStatus(String shelfStatus) {
		this.shelfStatus = shelfStatus;
	}
	public String getShelfType() {
		return shelfType;
	}
	public void setShelfType(String shelfType) {
		this.shelfType = shelfType;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

}
