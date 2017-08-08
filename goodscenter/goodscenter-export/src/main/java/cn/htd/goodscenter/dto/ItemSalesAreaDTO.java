package cn.htd.goodscenter.dto;

import java.util.List;

import cn.htd.goodscenter.domain.ItemSalesArea;
import cn.htd.goodscenter.domain.ItemSalesAreaDetail;

public class ItemSalesAreaDTO {
	private ItemSalesArea itemSalesArea;
	private List<ItemSalesAreaDetail> itemSalesAreaDateail;
	public ItemSalesArea getItemSalesArea() {
		return itemSalesArea;
	}
	public void setItemSalesArea(ItemSalesArea itemSalesArea) {
		this.itemSalesArea = itemSalesArea;
	}
	public List<ItemSalesAreaDetail> getItemSalesAreaDateail() {
		return itemSalesAreaDateail;
	}
	public void setItemSalesAreaDateail(
			List<ItemSalesAreaDetail> itemSalesAreaDateail) {
		this.itemSalesAreaDateail = itemSalesAreaDateail;
	}
	
	
}
