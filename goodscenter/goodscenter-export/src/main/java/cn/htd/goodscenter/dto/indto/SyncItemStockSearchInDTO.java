package cn.htd.goodscenter.dto.indto;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.htd.goodscenter.dto.common.AbstractPagerDTO;

public class SyncItemStockSearchInDTO extends AbstractPagerDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3549902346982839395L;
	
	private String skuCode;
	private String itemName;
	private Long sellerId;
	private Long firstCatId;
	private Long secondCatId;
	private Long thirdCatId;
	private String brandId;
	private List<Long> thirdCatIdList;
	//供应商编码
	private String supplierCode;
	// 新增erpCode
	private String erpCode;
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public Long getFirstCatId() {
		return firstCatId;
	}
	public void setFirstCatId(Long firstCatId) {
		this.firstCatId = firstCatId;
	}
	public Long getSecondCatId() {
		return secondCatId;
	}
	public void setSecondCatId(Long secondCatId) {
		this.secondCatId = secondCatId;
	}
	public Long getThirdCatId() {
		return thirdCatId;
	}
	public void setThirdCatId(Long thirdCatId) {
		this.thirdCatId = thirdCatId;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public List<Long> getThirdCatIdList() {
		return thirdCatIdList;
	}
	public void setThirdCatIdList(List<Long> thirdCatIdList) {
		this.thirdCatIdList = thirdCatIdList;
	}
	public boolean isEmpty(){
		
		if(StringUtils.isEmpty(skuCode)&&StringUtils.isEmpty(itemName)&&sellerId==null&&
				firstCatId==null&&secondCatId==null&&thirdCatId==null&&StringUtils.isEmpty(brandId)){
			return true;
		}
		return false;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}


	public String getErpCode() {
		return erpCode;
	}

	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}
}
