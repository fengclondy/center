package cn.htd.goodscenter.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.htd.common.DataGrid;

/**
 * 
 * <p>
 * Description: [商品搜索出参]
 * </p>
 */
public class SearchOutDTO implements Serializable {

	private static final long serialVersionUID = 4865608182872322797L;

	private List<ItemAttrDTO> attributes = new ArrayList<ItemAttrDTO>();// 商品非销售属性
	private List<ItemBrandDTO> brands = new ArrayList<ItemBrandDTO>();// 品牌
	private List<ItemCatCascadeDTO> categories = new ArrayList<ItemCatCascadeDTO>();// 商品类别
	private DataGrid<ItemSkuDTO> itemDTOs = new DataGrid<ItemSkuDTO>();// 搜索时商品

	public List<ItemAttrDTO> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<ItemAttrDTO> attributes) {
		this.attributes = attributes;
	}

	public List<ItemBrandDTO> getBrands() {
		return brands;
	}

	public void setBrands(List<ItemBrandDTO> brands) {
		this.brands = brands;
	}

	public List<ItemCatCascadeDTO> getCategories() {
		return categories;
	}

	public void setCategories(List<ItemCatCascadeDTO> categories) {
		this.categories = categories;
	}

	public DataGrid<ItemSkuDTO> getItemDTOs() {
		return itemDTOs;
	}

	public void setItemDTOs(DataGrid<ItemSkuDTO> itemDTOs) {
		this.itemDTOs = itemDTOs;
	}

}
