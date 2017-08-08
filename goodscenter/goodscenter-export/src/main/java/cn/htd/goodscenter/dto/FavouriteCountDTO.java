package cn.htd.goodscenter.dto;

import java.io.Serializable;

public class FavouriteCountDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer favouriteCount;// 收藏数量
	private ItemShopCartDTO itemShopCartDTO;// 商品信息

	public Integer getFavouriteCount() {
		return favouriteCount;
	}

	public void setFavouriteCount(Integer favouriteCount) {
		this.favouriteCount = favouriteCount;
	}

	public ItemShopCartDTO getItemShopCartDTO() {
		return itemShopCartDTO;
	}

	public void setItemShopCartDTO(ItemShopCartDTO itemShopCartDTO) {
		this.itemShopCartDTO = itemShopCartDTO;
	}

}
