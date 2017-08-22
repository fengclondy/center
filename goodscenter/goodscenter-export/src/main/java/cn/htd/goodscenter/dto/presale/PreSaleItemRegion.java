package cn.htd.goodscenter.dto.presale;

import java.io.Serializable;

public class PreSaleItemRegion implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9006865412071660223L;
	//地区编码
	private String regionCode;
	//地区名称
	private String regionName;
	//地区类别 1省 2市 3县/县级市/区
	private String regionType;
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getRegionType() {
		return regionType;
	}
	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}
	
}
