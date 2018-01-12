package cn.htd.goodscenter.dao;

import java.util.Date;
import java.util.List;

import cn.htd.goodscenter.dto.indto.QueryItemCategoryInDTO;
import org.apache.ibatis.annotations.Param;

import cn.htd.common.Pager;
import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.goodscenter.domain.ItemCategory;
import cn.htd.goodscenter.domain.ItemCategoryCascade;
import cn.htd.goodscenter.dto.ItemAttrDTO;
import cn.htd.goodscenter.dto.ItemAttrValueDTO;
import cn.htd.goodscenter.dto.ItemCategoryDTO;

public interface ItemCategoryDAO extends BaseDAO<ItemCategoryDTO> {
	/**
	 * 平台所有类目列表查询
	 */
	public List<ItemCategoryDTO> queryItemCategoryAllList(@Param("entity") ItemCategoryDTO itemCategoryDTO, @Param("page") Pager pager);

	/**
	 * 查询父级目录
	 * @param lev
	 * @param cids
	 * @return
	 */
	public List<ItemCategoryCascade> queryParentCats(@Param("lev") Integer lev, @Param("cids") Long[] cids);

	/**
	 * <p>
	 * Discription:[根据属性ID组查询属性]
	 * </p>
	 */
	public List<ItemAttrDTO> queryItemAttrList(@Param("keyList") List<String> attrIds);

	/**
	 * 
	 * <p>
	 * Discription:[根据商品属性ID，和商品属性值ID查询对应属性信息]
	 * </p>
	 * 
	 * @param key     商品属性ID
	 * @param value   商品属性值ID 集合 若此参数为空 则查询商品属性ID下的所有属性
	 */
	public List<ItemAttrValueDTO> queryItemAttrValueList(@Param("keyId") Long key, @Param("valueList") List<String> value);

	/**
	 * 
	 * <p>
	 * Discription:[根据类目ID查询类目ID下的所有三级类目]
	 * </p>
	 */
	public List<ItemCategory> queryThirdCatsList(Long cid);

	/**
	 * <p>
	 * Discription:[根据类目级别查询对应级别的所有类目]
	 * </p>
	 */
	public List<ItemCategoryDTO> queryItemByCategoryLev(@Param("categoryLev") Integer categoryLev);

	/**
	 * <p>
	 * Discription:[根据cid查询类目]
	 * </p>
	 */
	public ItemCategoryDTO getCategoryByChildCid(Long cid);
	
	/**
	 * 用于搜索引擎同步数据
	 * */
	public List<ItemCategoryCascade> queryCategoryBySyncTime(@Param("syncTime") Date syncTime);

	/**
	 * 根据类目名称查询类目ID
	 * @param categoryName
	 * @return
	 */
	public Long queryThirdCategoryIdByName(@Param("categoryName") String categoryName);

	/**
	 * 平台所有类目列表查询
	 */
	public List<ItemCategoryDTO> queryItemCategoryList4SB(@Param("entity") QueryItemCategoryInDTO itemCategoryDTO, @Param("page") Pager pager);

	public Long queryCountItemCategoryList4SB(@Param("entity") QueryItemCategoryInDTO itemCategoryDTO);

	/**
	 * 批量查询类目
	 * @param cidList
	 * @return
	 */
	List<ItemCategoryDTO> getCategoryListByCids(@Param("cidList") List<Long> cidList);
	
	/**
	 * 根据名称模糊查询品类
	 */
	public List<ItemCategoryDTO> queryItemCategoryList(@Param("entity") ItemCategoryDTO itemCategoryDTO, @Param("page") Pager pager);

	/**
	 * 根据类目名称查询类目ID
	 * @return
	 */
	List<ItemCategoryDTO> batchQueryThirdCategoryIdByName(@Param("cNameList") List<String> cNameList);

}
