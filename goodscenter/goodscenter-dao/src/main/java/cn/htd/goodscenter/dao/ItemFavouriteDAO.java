package cn.htd.goodscenter.dao;

import java.util.List;

import cn.htd.goodscenter.domain.ItemFavourite;
import cn.htd.goodscenter.dto.mall.ItemFavouriteInDTO;
import org.apache.ibatis.annotations.Param;
import cn.htd.common.Pager;

/**
 * <p>
 * Description: 商品收藏
 * </p>
 */
public interface ItemFavouriteDAO {
	/**
	 * 添加
	 * @param itemFavourite
	 */
	int insert(ItemFavourite itemFavourite);

	/**
	 * 查询
	 * @param itemFavourite
	 * @return ItemFavourite
	 */
	ItemFavourite select(@Param("entity") ItemFavourite itemFavourite);

	/**
	 * 根据主键查询查询
	 * @param id 主键id
	 * @return ItemFavourite
	 */
	ItemFavourite selectByPrimaryKey(Long id);

	/**
	 * 分页查询
	 */
	List<ItemFavourite> selectListPage(@Param("entity") ItemFavourite itemFavourite, @Param("page") Pager pager);

	/**
	 * 分页查询 - 查询总数量
	 */
	Long selectListPageCount(@Param("entity") ItemFavourite itemFavourite);

	/**
	 * 删除
	 * @param id 主键id
	 */
	void deleteByPrimaryKey(Long id);

	/**
	 * 批量删除
	 * @param itemFavouriteIdList ids
	 */
	void batchDelete(List<Long> itemFavouriteIdList);

	/**
	 * 更新
	 * @param itemFavourite
	 */
	int delete(ItemFavourite itemFavourite);
}
