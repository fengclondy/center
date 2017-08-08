package cn.htd.storecenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.storecenter.dto.ShopBrandCategoryDTO;

/**
 * 
 * 根据品牌品类查询商家Id
 */
public interface ShopBrandCategoryService {
	/**
	 * 
	 * <p>
	 * Discription:[根据品牌 品类查询 供应商]
	 * </p>
	 */
	public ExecuteResult<DataGrid<Long>> queryShopIdList(ShopBrandCategoryDTO shopBrandCategoryDTO);

}
