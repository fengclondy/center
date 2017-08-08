package cn.htd.storecenter.service;

import java.util.List;

import cn.htd.common.ExecuteResult;
import cn.htd.storecenter.dto.ShopTemplatesDTO;

/**
 * 
 * <p>
 * Description: [平台级店铺模板]
 * </p>
 */
public interface ShopTemplatesService {

	/**
	 * 
	 * <p>
	 * Discription:[查询平台店铺模板List]
	 * </p>
	 * 
	 * @TODO 方法名要改
	 */
	public ExecuteResult<List<ShopTemplatesDTO>> createShopTemplatesList(ShopTemplatesDTO shopTemplatesDTO);

	/**
	 * 
	 * <p>
	 * Discription:[修改店铺模板状态]
	 * </p>
	 */
	public ExecuteResult<String> modifyShopTemplatesStatus(ShopTemplatesDTO dto);

	/**
	 * 
	 * <p>
	 * Discription:[修改店铺模板颜色]
	 * </p>
	 * 
	 * @param templatesId
	 * @param color
	 */
	public ExecuteResult<String> modifyShopTemplatesColor(ShopTemplatesDTO dto);

}
