package cn.htd.storecenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.storecenter.dto.ShopModifyDetailDTO;

/**
 * 店铺信息修改详情
 */
public interface ShopModifyDetailExportService {

	/**
	 * 
	 * <p>
	 * Discription:[店铺信息修改详情]
	 * </p>
	 */
	public ExecuteResult<DataGrid<ShopModifyDetailDTO>> queryShopModifyDetail(ShopModifyDetailDTO shopModifyDetailDTO,
			Pager<ShopModifyDetailDTO> page);
}
