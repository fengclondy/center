package cn.htd.storecenter.service;

import java.util.List;

import cn.htd.common.ExecuteResult;
import cn.htd.storecenter.dto.ShopWidgetDTO;

/**
 * <p>
 * Description: [店铺控件(自定义装修)接口]
 * </p>
 */
public interface ShopWidgetService {

	/**
	 * <p>
	 * Discription:[保存或更新控件]
	 * </p>
	 */
	ExecuteResult<Boolean> saveOrUpdateWidget(ShopWidgetDTO widgetDTO);

	/**
	 * <p>
	 * Discription:[删除控件]
	 * </p>
	 */
	ExecuteResult<Boolean> deleteWidget(long frameId, String widgetId);

	/**
	 * <p>
	 * Discription:[删除控件]
	 * </p>
	 */
	ExecuteResult<Boolean> deleteWidget(long frameId);

	/**
	 * 
	 * <p>
	 * Discription:[查找控件]
	 * </p>
	 */
	ExecuteResult<List<ShopWidgetDTO>> findWidget(ShopWidgetDTO widgetDTO);
}
