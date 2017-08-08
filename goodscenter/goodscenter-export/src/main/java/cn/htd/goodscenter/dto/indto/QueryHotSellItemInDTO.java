package cn.htd.goodscenter.dto.indto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class QueryHotSellItemInDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7441511971198604228L;
	//供应商类型：0 平台公司 1 外部供应商
	@NotEmpty(message="supplierType不能为空")
	private String supplierType;
	//供应商编码
	@NotNull(message="supplierId不能为空")
	private Long supplierId;
	//店铺
	@NotNull(message="shopId不能为空")
	private Long shopId;
	//商品sku编码
	private String itemCode;
	//当前城市站点编码
	@NotEmpty(message="currentSiteCode不能为空")
	private String currentSiteCode;
	//是否包厢 0：非包厢 1：包厢
	private String isBoxFlag;
	//数量
	@NotNull(message="count不能为空")
	private Integer count;
	//是否登录标志：0 未登录 1 已登录
	@NotEmpty(message="isLoginFlag不能为空")
	private String isLoginFlag;
	//城市站子区域 （非必填）
	private List<String> siteChildCodeList;
	//省份编码（非必填）
	private String provinceSiteCode;
	 //是否归属于0801
    private boolean isBelongsHtd;
	public String getSupplierType() {
		return supplierType;
	}
	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getCurrentSiteCode() {
		return currentSiteCode;
	}
	public void setCurrentSiteCode(String currentSiteCode) {
		this.currentSiteCode = currentSiteCode;
	}
	public String getIsBoxFlag() {
		return isBoxFlag;
	}
	public void setIsBoxFlag(String isBoxFlag) {
		this.isBoxFlag = isBoxFlag;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getIsLoginFlag() {
		return isLoginFlag;
	}
	public void setIsLoginFlag(String isLoginFlag) {
		this.isLoginFlag = isLoginFlag;
	}
	public List<String> getSiteChildCodeList() {
		return siteChildCodeList;
	}
	public void setSiteChildCodeList(List<String> siteChildCodeList) {
		this.siteChildCodeList = siteChildCodeList;
	}
	public String getProvinceSiteCode() {
		return provinceSiteCode;
	}
	public void setProvinceSiteCode(String provinceSiteCode) {
		this.provinceSiteCode = provinceSiteCode;
	}
	public boolean isBelongsHtd() {
		return isBelongsHtd;
	}
	public void setBelongsHtd(boolean isBelongsHtd) {
		this.isBelongsHtd = isBelongsHtd;
	}
	
}
