package cn.htd.membercenter.dto;

import java.io.Serializable;
import java.util.List;

public class MemberRelationSearchDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3221456471066101813L;
	
	private String sellerId;
	
	private String brandId;
	
	private String categoryId;
	
	private int showType;
	
	private List<String> buyerIdList;
	
	private String companyName;
	
	private String artificialPersonName;
	
	private String locationProvince ;
	
	private String locationCity ;
	
	private String locationCounty ;
	
	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getArtificialPersonName() {
		return artificialPersonName;
	}

	public void setArtificialPersonName(String artificialPersonName) {
		this.artificialPersonName = artificialPersonName;
	}

	public int getShowType() {
		return showType;
	}

	public void setShowType(int showType) {
		this.showType = showType;
	}

	public List<String> getBuyerIdList() {
		return buyerIdList;
	}

	public void setBuyerIdList(List<String> buyerIdList) {
		this.buyerIdList = buyerIdList;
	}

	public String getLocationProvince() {
		return locationProvince;
	}

	public void setLocationProvince(String locationProvince) {
		this.locationProvince = locationProvince;
	}

	public String getLocationCity() {
		return locationCity;
	}

	public void setLocationCity(String locationCity) {
		this.locationCity = locationCity;
	}

	public String getLocationCounty() {
		return locationCounty;
	}

	public void setLocationCounty(String locationCounty) {
		this.locationCounty = locationCounty;
	}
}
