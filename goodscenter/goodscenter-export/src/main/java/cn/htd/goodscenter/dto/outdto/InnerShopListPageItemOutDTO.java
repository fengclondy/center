package cn.htd.goodscenter.dto.outdto;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.htd.goodscenter.domain.ItemPicture;

public class InnerShopListPageItemOutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -193651303671466788L;
	
	//主图
	private ItemPicture firstPic;
	//商品名称
	private String itemName;
	//包厢销售价
	private BigDecimal boxSalePrice;
	//区域销售价
	private BigDecimal areaSalePrice;

	public ItemPicture getFirstPic() {
		return firstPic;
	}

	public void setFirstPic(ItemPicture firstPic) {
		this.firstPic = firstPic;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public BigDecimal getBoxSalePrice() {
		return boxSalePrice;
	}

	public void setBoxSalePrice(BigDecimal boxSalePrice) {
		this.boxSalePrice = boxSalePrice;
	}

	public BigDecimal getAreaSalePrice() {
		return areaSalePrice;
	}

	public void setAreaSalePrice(BigDecimal areaSalePrice) {
		this.areaSalePrice = areaSalePrice;
	}
	

}
