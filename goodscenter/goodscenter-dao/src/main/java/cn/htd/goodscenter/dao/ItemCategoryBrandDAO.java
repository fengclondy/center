package cn.htd.goodscenter.dao;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.goodscenter.domain.ItemCategoryBrand;
import cn.htd.goodscenter.dto.ItemBrandDTO;

import java.util.List;

public interface ItemCategoryBrandDAO extends BaseDAO<ItemCategoryBrand> {

	/**
	 *
	 * <p>
	 * Discription:[根据三级类目 二级类目和 品牌名称 查询品牌，确定类目品牌唯一性，同一类目下不能存在名字相投的品牌]
	 * </p>
	 */
	ItemCategoryBrand queryCategoryBrandByBrandName(@Param("param") ItemBrandDTO param);

	/**
	 * <p>
	 * Discription:[根据三级类目 二级类目和 品牌ID 查询品牌，确定类目品牌唯一性]
	 * </p>
	 */
	List<ItemCategoryBrand> queryCategoryBrandByBrandId(@Param("param") ItemCategoryBrand itemCategoryBrand);

	/**
	 * 
	 * <p>
	 * Discription:[查询该品牌是否关联类目]
	 * </p>
	 */
	Long queryCbByBrandId(Long brandId);
}
