package cn.htd.goodscenter.dto.vms;

import cn.htd.goodscenter.dto.common.AbstractPagerDTO;

import java.io.Serializable;

/**
 * 
 * @author zhangxiaolong
 *
 */
public class QueryVmsItemPublishInfoInDTO extends AbstractPagerDTO implements Serializable{
	/**
	 * 卖家ID
	 */
	private Long sellerId;
	/**
	 * 商品编码
	 */
	private String productCode;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 一级类目ID
	 */
	private Long firstCid;
	/**
	 * 二级类目ID
	 */
	private Long secondCid;
	/**
	 * 三级类目ID
	 */
	private Long thirdCid;

	/**
	 * 品牌ID
	 */
	private Long brandId;

	/**
	 * 上架状态
	 * null 全部
	 * 0 下架 （上架过，但是下架了）
	 * 1 上架
	 * 2 未上架（从来没有上架过）
	 */
	private String shelfStatus;
	/**
	 * 上架类型
	 * 1 包厢
	 * 0 大厅
	 */
	private Long isBoxFlag;
	/**
	 * 供应商编码
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

	public Long getFirstCid() {
		return firstCid;
	}

	public void setFirstCid(Long firstCid) {
		this.firstCid = firstCid;
	}

	public Long getSecondCid() {
		return secondCid;
	}

	public void setSecondCid(Long secondCid) {
		this.secondCid = secondCid;
	}

	public Long getThirdCid() {
		return thirdCid;
	}

	public void setThirdCid(Long thirdCid) {
		this.thirdCid = thirdCid;
	}

	public String getShelfStatus() {
		return shelfStatus;
	}

	public void setShelfStatus(String shelfStatus) {
		this.shelfStatus = shelfStatus;
	}

	public Long getIsBoxFlag() {
		return isBoxFlag;
	}

	public void setIsBoxFlag(Long isBoxFlag) {
		this.isBoxFlag = isBoxFlag;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}
}
