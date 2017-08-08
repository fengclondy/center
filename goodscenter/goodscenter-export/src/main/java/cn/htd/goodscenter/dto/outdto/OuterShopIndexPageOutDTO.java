package cn.htd.goodscenter.dto.outdto;

import java.io.Serializable;
import java.util.List;

public class OuterShopIndexPageOutDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1785966839183899875L;
	// 新品上架
	private List<OuterShopListPageItemOutDTO> newShopItemList;
	// 默认全部商品
	private List<OuterShopListPageItemOutDTO> defaulAllShopItemList;

	public List<OuterShopListPageItemOutDTO> getNewShopItemList() {
		return newShopItemList;
	}

	public void setNewShopItemList(
			List<OuterShopListPageItemOutDTO> newShopItemList) {
		this.newShopItemList = newShopItemList;
	}

	public List<OuterShopListPageItemOutDTO> getDefaulAllShopItemList() {
		return defaulAllShopItemList;
	}

	public void setDefaulAllShopItemList(
			List<OuterShopListPageItemOutDTO> defaulAllShopItemList) {
		this.defaulAllShopItemList = defaulAllShopItemList;
	}

}
