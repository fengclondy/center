package cn.htd.storecenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.storecenter.dto.ShopFrameDTO;
import cn.htd.storecenter.dto.ShopModuleDTO;
import cn.htd.storecenter.dto.ShopWidgetDTO;

/**
 * <p>
 * Description: [店铺自定义装修接口]
 * </p>
 */
public interface ShopDecorationService {

	/**
	 * <p>
	 * Discription:[查找店铺主框架（布局、颜色）--发布版]
	 * </p>
	 */
	public ShopFrameDTO findPublishFrameByShopId(long shopId);

	/**
	 * <p>
	 * Discription:[查找店铺主框架（布局、颜色）--编辑版]
	 * </p>
	 */
	public ShopFrameDTO findDecorationFrameByShopId(long shopId);

	/**
	 * <p>
	 * Discription:[更新布局信息--编辑版]
	 * </p>
	 * 
	 * @param shopId
	 * @param headModulesJson
	 * @param bodyLayoutsJson
	 * @param footerModulesJson
	 */
	public void updateLayout(long shopId, String headModulesJson, String bodyLayoutsJson, String footerModulesJson);

	/**
	 * <p>
	 * Discription:[更新框架颜色--编辑版]
	 * </p>
	 * 
	 * @param shopId
	 * @param color
	 */
	public void updateColor(long shopId, String color);

	/**
	 * <p>
	 * Discription:[更新背景--编辑版]
	 * </p>
	 * 
	 * @param shopId
	 * @param bgColor 背景颜色
	 * @param showBgColor 是否显示背景颜色
	 * @param bgImgUrl 背景图片
	 * @param bgRepeat 重复规则
	 * @param bgAlign 对齐规则
	 */
	public void updateBackground(long shopId, String bgColor, boolean showBgColor, String bgImgUrl, String bgRepeat,
			String bgAlign);

	/**
	 * <p>
	 * Discription:[获得店铺模块信息]
	 * </p>
	 * 
	 * @param shopId
	 * @param widgetId
	 * @param isPublish 是否是发布版本， 否则是编辑版本
	 */
	public ShopModuleDTO findModuleByWidgetId(long shopId, String widgetId, boolean isPublish);

	/**
	 * <p>
	 * Discription:[查找Widget]
	 * </p>
	 * 
	 * @param shopId
	 * @param widgetId
	 * @param isPublish
	 */
	public ShopWidgetDTO findWidget(long shopId, String widgetId, boolean isPublish);

	/**
	 * <p>
	 * Discription:[设置Widget--编辑版]
	 * </p>
	 * 
	 * @param shopId
	 * @param widgetId
	 * @param widget
	 */
	public void setWidget(long shopId, String widgetId, ShopWidgetDTO widget);

	/**
	 * <p>
	 * Discription:[删除Widget--编辑版]
	 * </p>
	 * 
	 * @param shopId
	 * @param widgetIds
	 */
	public void delWidget(long shopId, String[] widgetIds);

	/**
	 * <p>
	 * Discription:[发布事件-- 将编辑数据替换]
	 * </p>
	 * 
	 * @param shopId
	 */
	public boolean decoration2Publish(long shopId);

	/**
	 * <p>
	 * Discription:[装修数据存入数据库]
	 * </p>
	 * 
	 * @param shopId
	 * @param versionType 版本类型：0 ：发布版， 1：编辑版，2： 历史版本
	 */
	public ExecuteResult<ShopFrameDTO> decoration2Db(long shopId, int versionType);

	/**
	 * <p>
	 * Discription:[数据库数据转到编辑redis中（可用于历史版本恢复）]
	 * </p>
	 * 
	 * @param frameId
	 * @param shopId
	 */
	public ExecuteResult<Boolean> db2decoration(long frameId, long shopId);

	/**
	 * <p>
	 * Discription:[删除装修数据， 慎用]
	 * </p>
	 * 
	 * @param shopId
	 */
	public void delDecoration(long shopId);
}
