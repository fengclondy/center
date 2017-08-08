package cn.htd.goodscenter.service.mall;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.dto.mall.ItemFavouriteInDTO;
import cn.htd.goodscenter.dto.mall.ItemFavouriteOutDTO;

import java.util.List;

/**
 * 商品收藏
 * @author chenkang
 * @date 2017-01-07
 */
public interface MallItemFavouriteExportService {
	/**
	 * 收藏商品
	 * @return 收藏结果
	 */
	ExecuteResult<String> favouriteItem(ItemFavouriteInDTO itemFavouriteInDTO);

	/**
	 * 批量取消收藏商品
	 * @return 取消收藏结果
	 */
	ExecuteResult<String> batchCancelFavouriteItem(List<Long> itemFavouriteIdList);

	/**
	 * 取消收藏商品
	 * @param userId
	 * @param skuId
	 * @return
	 */
	ExecuteResult<String> cancelFavouriteItem(Long userId, Long skuId);

	/**
	 * 查询收藏商品列表
	 * @return
	 */
	ExecuteResult<DataGrid<ItemFavouriteOutDTO>> queryFavouriteItemList(Long userId, Pager pager);

	/**
	 * 是否已经收藏
	 * @param userId
	 * @param skuId
	 * @return
	 */
	ExecuteResult<Boolean> isHasFavouriteItem(Long userId, Long skuId);
}
