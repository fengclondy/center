package cn.htd.tradecenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.tradecenter.dto.ShopFreightTemplateDTO;

/**
 * <p>
 * Description: [运费模板service层]
 * </p>
 */
public interface ShopFreightTemplateExportService {

	/**
	 * <p>
	 * Discription:[运费模板修改]
	 * </p>
	 */
	public ExecuteResult<ShopFreightTemplateDTO> update(ShopFreightTemplateDTO dto);

	/**
	 * <p>
	 * Discription:[运费模板分页查询]
	 * </p>
	 * 
	 * @param dto
	 * @param pager
	 */
	public ExecuteResult<DataGrid<ShopFreightTemplateDTO>> queryShopFreightTemplateList(ShopFreightTemplateDTO dto,
			Pager<ShopFreightTemplateDTO> pager);

	/**
	 * <p>
	 * Discription:[运费模板单个删除]
	 * </p>
	 */
	public ExecuteResult<String> deleteShopFreightTemplateById(long id);

	/**
	 * <p>
	 * Discription:[运费模板添加]
	 * </p>
	 */
	public ExecuteResult<ShopFreightTemplateDTO> addShopFreightTemplate(ShopFreightTemplateDTO dto);

	/**
	 * <p>
	 * Discription:[运费模板添加,当选择卖家自定义的时候]
	 * </p>
	 */
	public ExecuteResult<ShopFreightTemplateDTO> addShopFreightTemplateForSeller (ShopFreightTemplateDTO dto);

	/**
	 * 根据id获取运费模板内容
	 */
	public ShopFreightTemplateDTO queryShopFreightTemplateById(Long id);

	/**
	 * 
	 * <p>
	 * Description: [根据ID查询]
	 * </p>
	 * 
	 * @param shopFreightTemplateId
	 */
	ExecuteResult<ShopFreightTemplateDTO> queryById(Long shopFreightTemplateId);

	/**
	 * 
	 * <p>
	 * Description: [根据运费模版Id查询运费模版，同时把运费策略和优惠策略查出来，并根据regionId过滤运费策略]
	 * </p>
	 * 
	 * @param regionId
	 * @param shopFreightTemplateId
	 */
	ExecuteResult<ShopFreightTemplateDTO> queryByRegionIdAndTemplateId(Long regionId, Long shopFreightTemplateId);

	/**
	 * 
	 * <p>
	 * Description: [模版复制]
	 * </p>
	 * 
	 * @param shopFreightTemplateId
	 * 
	 */
	public ExecuteResult<ShopFreightTemplateDTO> copy(Long shopFreightTemplateId);
}
