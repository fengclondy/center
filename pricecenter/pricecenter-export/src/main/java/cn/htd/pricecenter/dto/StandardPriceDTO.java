package cn.htd.pricecenter.dto;

import cn.htd.pricecenter.domain.InnerItemSkuPrice;
import cn.htd.pricecenter.domain.ItemSkuBasePrice;

import java.io.Serializable;
import java.util.List;

public class StandardPriceDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6580641360638042029L;
	private ItemSkuBasePrice itemSkuBasePrice;
	//会员分组价
	private List<InnerItemSkuPrice> itemSkuMemberGroupPriceList;
	//会员等级价
	private List<InnerItemSkuPrice> itemSkuMemberLevelPriceList;
	//区域会员等级价
	private List<InnerItemSkuPrice> boxLevelPriceList;
	//包厢会员等级价
	private List<InnerItemSkuPrice>	areaLevelPriceList;
	//区域价
	private List<InnerItemSkuPrice>  areaPriceList;
	public ItemSkuBasePrice getItemSkuBasePrice() {
		return itemSkuBasePrice;
	}
	public void setItemSkuBasePrice(ItemSkuBasePrice itemSkuBasePrice) {
		this.itemSkuBasePrice = itemSkuBasePrice;
	}
	public List<InnerItemSkuPrice> getItemSkuMemberGroupPriceList() {
		return itemSkuMemberGroupPriceList;
	}
	public void setItemSkuMemberGroupPriceList(
			List<InnerItemSkuPrice> itemSkuMemberGroupPriceList) {
		this.itemSkuMemberGroupPriceList = itemSkuMemberGroupPriceList;
	}
	public List<InnerItemSkuPrice> getItemSkuMemberLevelPriceList() {
		return itemSkuMemberLevelPriceList;
	}
	public void setItemSkuMemberLevelPriceList(
			List<InnerItemSkuPrice> itemSkuMemberLevelPriceList) {
		this.itemSkuMemberLevelPriceList = itemSkuMemberLevelPriceList;
	}
	public List<InnerItemSkuPrice> getAreaPriceList() {
		return areaPriceList;
	}
	public void setAreaPriceList(List<InnerItemSkuPrice> areaPriceList) {
		this.areaPriceList = areaPriceList;
	}

	public List<InnerItemSkuPrice> getBoxLevelPriceList() {
		return boxLevelPriceList;
	}

	public void setBoxLevelPriceList(List<InnerItemSkuPrice> boxLevelPriceList) {
		this.boxLevelPriceList = boxLevelPriceList;
	}

	public List<InnerItemSkuPrice> getAreaLevelPriceList() {
		return areaLevelPriceList;
	}

	public void setAreaLevelPriceList(List<InnerItemSkuPrice> areaLevelPriceList) {
		this.areaLevelPriceList = areaLevelPriceList;
	}
}
