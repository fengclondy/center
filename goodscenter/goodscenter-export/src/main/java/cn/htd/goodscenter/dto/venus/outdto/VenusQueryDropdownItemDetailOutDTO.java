package cn.htd.goodscenter.dto.venus.outdto;

import java.io.Serializable;
import java.util.List;

import cn.htd.goodscenter.dto.middleware.outdto.QueryItemWarehouseOutDTO;

public class VenusQueryDropdownItemDetailOutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8911769317125771750L;
	private String itemName;
	private String skuCode;
	private String itemCode;
	private List<VenusDropdownItemObjDTO> venusDropdownItemObjList;
	private List<QueryItemWarehouseOutDTO> itemWarehouseList;
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public List<QueryItemWarehouseOutDTO> getItemWarehouseList() {
		return itemWarehouseList;
	}
	public void setItemWarehouseList(
			List<QueryItemWarehouseOutDTO> itemWarehouseList) {
		this.itemWarehouseList = itemWarehouseList;
	}
	public List<VenusDropdownItemObjDTO> getVenusDropdownItemObjList() {
		return venusDropdownItemObjList;
	}
	public void setVenusDropdownItemObjList(
			List<VenusDropdownItemObjDTO> venusDropdownItemObjList) {
		this.venusDropdownItemObjList = venusDropdownItemObjList;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
}
