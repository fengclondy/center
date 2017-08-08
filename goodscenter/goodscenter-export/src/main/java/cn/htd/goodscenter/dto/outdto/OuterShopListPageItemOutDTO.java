package cn.htd.goodscenter.dto.outdto;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.htd.goodscenter.domain.ItemPicture;

public class OuterShopListPageItemOutDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6442979368545495498L;
	//主图
	private ItemPicture firstPic;
	//商品名称
	private String itemName;
	//最小阶梯价
	private BigDecimal ladderPriceStart;
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
	public BigDecimal getLadderPriceStart() {
		return ladderPriceStart;
	}
	public void setLadderPriceStart(BigDecimal ladderPriceStart) {
		this.ladderPriceStart = ladderPriceStart;
	}
	
	
}
