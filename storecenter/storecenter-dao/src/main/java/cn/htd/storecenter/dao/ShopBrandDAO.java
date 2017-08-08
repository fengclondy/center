package cn.htd.storecenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.storecenter.dto.ShopBrandDTO;

/**
 * <p>
 * 数据交互接口
 * </p>
 * 
 * @author
 * @createDate
 **/
public interface ShopBrandDAO extends BaseDAO<ShopBrandDTO> {

	/**
	 * 
	 * <p>
	 * Discription:[修改店铺状态]
	 * </p>
	 */
	Integer modifyShopCategoryStatus(ShopBrandDTO shopBrandDTO);

	/**
	 * 
	 * <p>
	 * Discription:[根据店铺ID状态查询店铺品牌]
	 * </p>
	 */
	List<ShopBrandDTO> selectByShopId(@Param("shopId") Long shopId, @Param("status") Integer status);

	/**
	 * 
	 * <p>
	 * Discription:[查询所有类目]
	 * </p>
	 */
	Long selectCountByConditionAll(@Param("entity") ShopBrandDTO shopBrandDTO);

	/**
	 * 
	 * <p>
	 * Discription:[查询所有类目数量]
	 * </p>
	 */
	List<ShopBrandDTO> selectListByConditionAll(@Param("entity") ShopBrandDTO shopBrandDTO, @Param("page") Pager page);

	/**
	 * 
	 * <p>
	 * Discription:[根据店铺ID删除品牌]
	 * </p>
	 */
	void deleteByShopId(Long id);

	/**
	 *
	 * <p>
	 * Discription:[根据ID删除品牌]
	 * </p>
	 */
	void delete(Long id);

	/**
	 * 
	 * <p>
	 * Discription:[根据id查找品牌所有信息]
	 * </p>
	 */
	List<ShopBrandDTO> selectBrandIdById(Long id);

	/**
	 * 
	 * <p>
	 * Discription:[根据BrandId修改曾经被驳回的品牌的status的值为-1]
	 * </p>
	 */
	void updateStatusByIdAndBrandId(ShopBrandDTO dto);
}