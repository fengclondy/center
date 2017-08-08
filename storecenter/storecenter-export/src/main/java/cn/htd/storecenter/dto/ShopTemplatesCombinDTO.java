package cn.htd.storecenter.dto;

import java.io.Serializable;
import java.util.Map;

public class ShopTemplatesCombinDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, ShopRenovationDTO> ShopRenovationmap;// 模板信息map

	private ShopTemplatesDTO shopTemplatesDTO; // 店铺模板类

	public Map<String, ShopRenovationDTO> getShopRenovationmap() {
		return ShopRenovationmap;
	}

	public void setShopRenovationmap(Map<String, ShopRenovationDTO> shopRenovationmap) {
		ShopRenovationmap = shopRenovationmap;
	}

	public ShopTemplatesDTO getShopTemplatesDTO() {
		return shopTemplatesDTO;
	}

	public void setShopTemplatesDTO(ShopTemplatesDTO shopTemplatesDTO) {
		this.shopTemplatesDTO = shopTemplatesDTO;
	}

}
