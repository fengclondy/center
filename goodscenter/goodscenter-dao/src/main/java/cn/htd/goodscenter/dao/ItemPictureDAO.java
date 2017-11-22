package cn.htd.goodscenter.dao;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.goodscenter.domain.ItemPicture;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemPictureDAO extends BaseDAO<ItemPicture> {

	/**
	 * 
	 * <p>
	 * Discription:[根据商品ID获取商品图片]
	 * </p>
	 */
	List<ItemPicture> queryItemPicsById(@Param("itemId") Long itemId);

	/**
	 * 
	 * <p>
	 * Discription:[根据商品ID删除商品图片]
	 * </p>
	 */
	void deleteItemPicture(@Param("itemId") Long itemId);
	
	/**
	 * 批量新增商品图片
	 * 
	 * @param list
	 */
	void batchInsert(List<ItemPicture> list);

	int updateDeleteFlagByItemId(@Param("itemId") Long itemId);

	int updateDeleteFlagByItemIdAndSellerId(@Param("itemId") Long itemId,@Param("sellerId") Long sellerId);


	List<ItemPicture> selectAllJdPic();
	
	List<ItemPicture> queryItemPicsFirst(@Param("itemId") Long itemId);
}
