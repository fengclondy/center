package cn.htd.storecenter.dao;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.storecenter.dto.ShopWidgetSheetDTO;

/**
 * <p>
 * Description: [店铺自定义装修控件单条数据DAO]
 * </p>
 */
public interface ShopWidgetSheetDAO extends BaseDAO<ShopWidgetSheetDTO> {

	/**
	 * <p>
	 * Discription:[删除]
	 * </p>
	 * 
	 * @param @param frameId
	 * @param @param widgetId
	 */
	public Integer delete(@Param("frameId") long frameId, @Param("widgetId") String widgetId);

	/**
	 * <p>
	 * Discription:[通过frameID删除所有]
	 * </p>
	 * 
	 * @param frameId
	 */
	public Integer deleteByFrameId(@Param("frameId") long frameId);
}
