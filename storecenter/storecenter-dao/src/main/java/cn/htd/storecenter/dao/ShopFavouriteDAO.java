package cn.htd.storecenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.storecenter.dto.ShopFavouriteDTO;

/**
 * <p>
 * Description: 店铺收藏
 * </p>
 */
public interface ShopFavouriteDAO {

	/**
	 * 
	 * <p>
	 * Discription:收藏
	 * </p>
	 */
	public void add(ShopFavouriteDTO favourite);

	/**
	 * 
	 * <p>
	 * Discription:收藏查询
	 * </p>
	 */
	public List<ShopFavouriteDTO> queryPage(@Param("page") Pager pager, @Param("entity") ShopFavouriteDTO favourite);

	/**
	 * 
	 * <p>
	 * Discription:收藏查询总数
	 * </p>
	 */
	public Integer queryPageCount(@Param("entity") ShopFavouriteDTO favourite);

	/**
	 * 
	 * <p>
	 * Discription:删除
	 * </p>
	 */
	public void del(ShopFavouriteDTO favourite);

	public List<ShopFavouriteDTO> list(@Param("entity") ShopFavouriteDTO favourite);

	public void updateModifyTime(@Param("id") Long id);
}
