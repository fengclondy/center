package cn.htd.storecenter.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * <p>
 * Description: [店铺信息修改入参]
 * </p>
 */
public class ShopInfoModifyInDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private ShopDTO shopDTO;// 店铺信息

	private List<ShopBrandDTO> shopBrandList;// 店铺品牌List

	private List<ShopCategoryDTO> shopCategoryList;// 店铺类目List

	public ShopDTO getShopDTO() {
		return shopDTO;
	}

	public void setShopDTO(ShopDTO shopDTO) {
		this.shopDTO = shopDTO;
	}

	public List<ShopBrandDTO> getShopBrandList() {
		return shopBrandList;
	}

	public void setShopBrandList(List<ShopBrandDTO> shopBrandList) {
		this.shopBrandList = shopBrandList;
	}

	public List<ShopCategoryDTO> getShopCategoryList() {
		return shopCategoryList;
	}

	public void setShopCategoryList(List<ShopCategoryDTO> shopCategoryList) {
		this.shopCategoryList = shopCategoryList;
	}

}
