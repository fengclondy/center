package cn.htd.goodscenter.dto.venus.po;

import java.io.Serializable;

import cn.htd.goodscenter.dto.common.AbstractPagerDTO;

/**
 * venus系统查询商品列表参数
 * 
 * @author zhangxiaolong
 *
 */
public class QueryVenusItemListParamDTO extends AbstractPagerDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3699734702226589019L;
	//产品编码
	private String productCode;
	//商品名称
	private String productName;
	//品类
	private String categoryName;
	//品牌
	private String brandName;
	//型号
	private String serial;
	//商家ID
	private Long htdVendorId;
	//商品状态
	private String productStatus;
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
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public Long getHtdVendorId() {
		return htdVendorId;
	}
	public void setHtdVendorId(Long htdVendorId) {
		this.htdVendorId = htdVendorId;
	}
	public String getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}
	
}
