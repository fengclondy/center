package cn.htd.goodscenter.dto.outdto;

import java.io.Serializable;
import java.util.List;

public class InnerShopIndexPageOutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8074892257101972383L;
	
	//新品上架
	private List<InnerShopListPageItemOutDTO> newShopItemList;
	//默认全部商品
	private List<InnerShopListPageItemOutDTO> defaulAllShopItemList;
	public List<InnerShopListPageItemOutDTO> getNewShopItemList() {
		return newShopItemList;
	}
	public void setNewShopItemList(List<InnerShopListPageItemOutDTO> newShopItemList) {
		this.newShopItemList = newShopItemList;
	}
	public List<InnerShopListPageItemOutDTO> getDefaulAllShopItemList() {
		return defaulAllShopItemList;
	}
	public void setDefaulAllShopItemList(
			List<InnerShopListPageItemOutDTO> defaulAllShopItemList) {
		this.defaulAllShopItemList = defaulAllShopItemList;
	}
	
}
