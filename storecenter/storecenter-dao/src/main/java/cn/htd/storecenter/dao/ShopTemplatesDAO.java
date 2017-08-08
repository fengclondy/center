package cn.htd.storecenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.storecenter.dto.ShopTemplatesDTO;

public interface ShopTemplatesDAO extends BaseDAO<ShopTemplatesDTO> {

	/**
	 * 
	 * <p>
	 * Discription:[修改模板状态]
	 * </p>
	 * 
	 * @param templatesId
	 * @param status
	 */
	void updateStatus(ShopTemplatesDTO shopTemplatesDTO);

	/**
	 * 
	 * <p>
	 * Discription:[根据店铺ID查询模板]
	 * </p>
	 * 
	 * @param shopId
	 * @return
	 */
	ShopTemplatesDTO selectByShopId(Long shopId);

	/**
	 * 
	 * <p>
	 * Discription:[修改店铺模板颜色]
	 * </p>
	 * 
	 * @param shopTemplatesDTO
	 */
	void updateColor(ShopTemplatesDTO shopTemplatesDTO);

}
