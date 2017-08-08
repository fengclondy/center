package cn.htd.tradecenter.service;

import java.util.List;

import cn.htd.common.ExecuteResult;
import cn.htd.tradecenter.dto.ShopDeliveryTypeDTO;

/**
 * <p>
 * Description: [运送方式service]
 * </p>
 */
public interface ShopDeliveryTypeExportService {

	/**
	 * <p>
	 * Discription:[运送方式添加]
	 * </p>
	 */
	public ExecuteResult<String> addShopDeliveryType(ShopDeliveryTypeDTO dto);

	/**
	 * <p>
	 * Discription:[运送方式删除]
	 * </p>
	 */
	public ExecuteResult<String> deleteShopDeliveryType(ShopDeliveryTypeDTO dto);

	/**
	 * <p>
	 * Discription:[运送方式修改]
	 * </p>
	 */
	public ExecuteResult<String> updateShopDeliveryType(ShopDeliveryTypeDTO dto);

	/**
	 * <p>
	 * Discription:[运送方式添加]
	 * </p>
	 */
	public ExecuteResult<List<ShopDeliveryTypeDTO>> queryShopDeliveryType(ShopDeliveryTypeDTO dto);

	/**
	 * 
	 * <p>
	 * Description: [根据运费模版Id和地区Id查询运费策略，如果找不到运费策略，就使用默认的（全国）运费策略]
	 * </p>
	 * 
	 * @param regionId   地区ID
	 * @param shopFreightTemplateId  运费模版ID
	 */
	ExecuteResult<List<ShopDeliveryTypeDTO>> queryByRegionIdAndTemplateId(Long regionId, Long shopFreightTemplateId);
}
