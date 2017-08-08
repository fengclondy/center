package cn.htd.storecenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.storecenter.dto.ShopBrandCategoryDTO;

/**
 * @author
 * @createDate
 **/
public interface ShopBrandCategoryDAO extends BaseDAO<ShopBrandCategoryDTO> {

	/**
	 * 
	 * 
	 * 根据类目id 品牌id查询商家Id
	 * 
	 */
	List<Long> selectByBrandIdCategoryId(@Param("shopBrandCategoryDTO") ShopBrandCategoryDTO shopBrandCategoryDTO);

}