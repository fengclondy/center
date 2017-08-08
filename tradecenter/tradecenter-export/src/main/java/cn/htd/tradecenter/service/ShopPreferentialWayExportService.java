package cn.htd.tradecenter.service;

import java.util.List;

import cn.htd.common.ExecuteResult;
import cn.htd.tradecenter.dto.ShopPreferentialWayDTO;

/**
 * <p>
 * Description: [优惠方式service]
 * </p>
 */
public interface ShopPreferentialWayExportService {

	/**
	 * <p>
	 * Discription:[优惠方式添加]
	 * </p>
	 * 
	 * @param dto
	 */
	public ExecuteResult<String> addShopPreferentialWay(ShopPreferentialWayDTO dto);

	/**
	 * <p>
	 * Discription:[优惠方式删除]
	 * </p>
	 */
	public ExecuteResult<String> deleteShopPreferentialWay(ShopPreferentialWayDTO dto);

	/**
	 * <p>
	 * Discription:[优惠方式修改]
	 * </p>
	 */
	public ExecuteResult<String> updateShopPreferentialWay(ShopPreferentialWayDTO dto);

	/**
	 * <p>
	 * Discription:[优惠方式添加]
	 * </p>
	 */
	public ExecuteResult<List<ShopPreferentialWayDTO>> queryShopPreferentialWay(ShopPreferentialWayDTO dto);
}
