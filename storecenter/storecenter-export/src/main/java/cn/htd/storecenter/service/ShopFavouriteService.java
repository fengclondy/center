package cn.htd.storecenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.storecenter.dto.ShopFavouriteDTO;

/**
 * <p>
 * Description: 店铺收藏
 * </p>
 */
public interface ShopFavouriteService {

	/**
	 * 
	 * <p>
	 * Discription:收藏
	 * </p>
	 */
	public ExecuteResult<String> addFavouriteShop(ShopFavouriteDTO favourite);

	/**
	 * 
	 * <p>
	 * Discription:收藏查询
	 * </p>
	 */
	public DataGrid<ShopFavouriteDTO> queryFavouriteShopList(ShopFavouriteDTO favourite, Pager<ShopFavouriteDTO> pager);

	/**
	 * 
	 * <p>
	 * Discription:收藏删除
	 * </p>
	 */
	public ExecuteResult<String> deleteFavouriteShop(ShopFavouriteDTO favourite);
}
