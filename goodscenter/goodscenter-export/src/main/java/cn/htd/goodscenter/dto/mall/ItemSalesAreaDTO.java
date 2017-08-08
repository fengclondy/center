package cn.htd.goodscenter.dto.mall;

import java.io.Serializable;
import java.util.List;

import cn.htd.common.dto.AddressInfo;

public class ItemSalesAreaDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7150992940052317358L;
	//是否全国标记 1 代表全国
	private Integer isSalesWholeCountry;
	//城市集合
	private List<AddressInfo> cityList;
	public Integer getIsSalesWholeCountry() {
		return isSalesWholeCountry;
	}
	public void setIsSalesWholeCountry(Integer isSalesWholeCountry) {
		this.isSalesWholeCountry = isSalesWholeCountry;
	}
	public List<AddressInfo> getCityList() {
		return cityList;
	}
	public void setCityList(List<AddressInfo> cityList) {
		this.cityList = cityList;
	}
	
	
	
}
