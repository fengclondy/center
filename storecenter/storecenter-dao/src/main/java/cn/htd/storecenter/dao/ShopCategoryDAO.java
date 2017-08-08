package cn.htd.storecenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.storecenter.dto.ShopCategoryDTO;

/**
 * <p>
 * Description: [店铺经营类目]
 * </p>
 */
public interface ShopCategoryDAO extends BaseDAO<ShopCategoryDTO> {

	/**
	 * 
	 * <p>
	 * Discription:[根据店铺id查询店铺经营类目]
	 * </p>
	 */
	List<ShopCategoryDTO> selectByShopId(@Param("shopId") Long shopId, @Param("status") Integer status);

	/**
	 * 
	 * <p>
	 * Discription:[根据ID修改店铺经营类目状态]
	 * </p>
	 */
	Integer modifyShopCategoryStatus(ShopCategoryDTO shopCategoryDTO);

	/**
	 * 
	 * <p>
	 * Discription:[查询店铺类目 所有]
	 * </p>
	 */
	List<ShopCategoryDTO> selectListByConditionAll(@Param("entity") ShopCategoryDTO shopCategoryDTO,
			@Param("page") Pager page);

	/**
	 * 
	 * <p>
	 * Discription:[查询店铺类目条数 所有]
	 * </p>
	 */
	Long selectCountByConditionAll(@Param("entity") ShopCategoryDTO shopCategoryDTO);

	/**
	 * 
	 * <p>
	 * Discription:[根据店铺ID删除类目]
	 * </p>
	 */
	void deleteByShopId(ShopCategoryDTO shopCategoryDTO);

	/**
	 * 
	 * <p>
	 * Discription:[根据id查找类目所有信息]
	 * </p>
	 */
	List<ShopCategoryDTO> selectShopIdById(Long Id);

	/**
	 * 
	 * <p>
	 * Discription:[根据shopId修改曾经被驳回的类目的status的值为-1]
	 * </p>
	 */
	void updateStatusByIdAndShopId(ShopCategoryDTO shopCategoryDTO);

	/**
	 * @Title: getShopCategory
	 * @Description: 根据卖家ID查询对应平台三级类目
	 */
	List<ShopCategoryDTO> getShopCategoryBysellerId(Long sellerId);

}
