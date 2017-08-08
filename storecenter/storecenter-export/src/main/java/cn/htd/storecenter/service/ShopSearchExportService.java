package cn.htd.storecenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.storecenter.dto.ShopDTO;

public interface ShopSearchExportService {

	/**
	 * 
	 * <p>
	 * Discription:[搜索店铺]
	 * </p>
	 */
	public ExecuteResult<DataGrid<ShopDTO>> searchShop(ShopDTO inDTO, Pager page);

}
