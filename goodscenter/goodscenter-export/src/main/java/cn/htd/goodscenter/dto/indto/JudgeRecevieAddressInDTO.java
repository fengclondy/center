package cn.htd.goodscenter.dto.indto;

import cn.htd.common.dto.GenricReqDTO;

public class JudgeRecevieAddressInDTO extends GenricReqDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2160139162950605601L;
	//省
	private String provinceCode;
	//市
	private String cityCode;
	//区
	private String districtCode;
	//sku编码
	private String skuCode;
	//是否包厢 0：非包厢 1：包厢
	private String isBoxFlag;
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getIsBoxFlag() {
		return isBoxFlag;
	}
	public void setIsBoxFlag(String isBoxFlag) {
		this.isBoxFlag = isBoxFlag;
	}
	
}
