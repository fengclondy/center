package cn.htd.storecenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.storecenter.dto.ShopRenovationDTO;
import cn.htd.storecenter.dto.ShopTemplatesCombinDTO;

/**
 * 
 * <p>
 * Description: [店铺装修]
 * </p>
 */
public interface ShopRenovationService {

	/**
	 * 
	 * <p>
	 * Discription:[查询店铺装修内容]
	 * </p>
	 */
	public ExecuteResult<ShopTemplatesCombinDTO> queryShopRenovation(ShopRenovationDTO shopRenovationDTO);

	/**
	 * 
	 * <p>
	 * Discription:[查询店铺装修内容]
	 * </p>
	 */
	public ExecuteResult<ShopTemplatesCombinDTO> queryShopRenovationByShopId(Long shopId);

	/**
	 * 
	 * <p>
	 * Discription:[ 添加店铺装修]
	 * </p>
	 */
	public ExecuteResult<String> addShopRenovation(ShopRenovationDTO shopRenovationDTO);

	/**
	 * 
	 * <p>
	 * Discription:[店铺装修修改]
	 * </p>
	 */
	public ExecuteResult<String> modifyShopRenovation(ShopRenovationDTO shopRenovationDTO);
}
