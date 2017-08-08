package cn.htd.storecenter.dao;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.storecenter.dto.ShopWidgetDTO;

/**
 * <p>
 * Description: [店铺自定义装修控件DAO]
 * </p>
 */
public interface ShopWidgetDAO extends BaseDAO<ShopWidgetDTO> {

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
