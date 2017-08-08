package cn.htd.storecenter.dao;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.storecenter.dto.ShopCategorySellerDTO;

import java.util.List;

/**
 * 
 * <p>
 * Description: [店铺经营类目]
 * </p>
 */
public interface ShopCategorySellerDAO extends BaseDAO<ShopCategorySellerDTO> {

	public int insertShopCategory(ShopCategorySellerDTO dto);

	public int deletes(@Param("entity") ShopCategorySellerDTO dto);

	public int delete(ShopCategorySellerDTO dto);

	public String selectNameByCondition(ShopCategorySellerDTO dto);

	public List<ShopCategorySellerDTO> queryChildCategory(@Param("entity") ShopCategorySellerDTO dto,@Param("count") Integer count);

	public ShopCategorySellerDTO selectParentCname(Long cid);
}