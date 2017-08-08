package cn.htd.goodscenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.htd.common.dao.orm.BaseDAO;
import cn.htd.goodscenter.domain.ItemAttrBean;
import cn.htd.goodscenter.domain.ItemAttrSeller;
import cn.htd.goodscenter.domain.ItemAttrValueBean;
import cn.htd.goodscenter.domain.ItemAttrValueSeller;
import cn.htd.goodscenter.dto.CatAttrSellerDTO;
import cn.htd.goodscenter.dto.CategoryAttrDTO;
import cn.htd.goodscenter.dto.ItemCategoryDTO;
import cn.htd.goodscenter.dto.outdto.QueryChildCategoryOutDTO;

public interface CategoryAttrDAO extends BaseDAO<CategoryAttrDTO> {
	/**
	 * 添加商品属性
	 */
	public int addAttr(CategoryAttrDTO categoryAttrDTO);

	/**
	 * 添加商品属性值
	 */
	public void addAttrValue(CategoryAttrDTO categoryAttrDTO);

	/**
	 * 添加商品类别属性关系值
	 */
	public void addCategoryAttrValue(CategoryAttrDTO categoryAttrDTO);

	/**
	 * 根据类目id和商品type查出属性name和属性id
	 */
	public List<CategoryAttrDTO> queryAttrNameList(CategoryAttrDTO categoryAttrDTO);

	/**
	 * 根据属性id查出属性值name和属性值id
	 */
	public List<CategoryAttrDTO> queryValueNameList(CategoryAttrDTO categoryAttrDTO);

	/**
	 * 
	 * <p>
	 * Discription:[查询卖家店铺类目属性]
	 * </p>
	 * 
	 * @param inDTO inDTO.attrType : 属性类型:1:销售属性;2:非销售属性
	 */
	public List<ItemAttrSeller> queryAttrSellerList(@Param("param") CatAttrSellerDTO inDTO);

	/**
	 * 
	 * <p>
	 * Discription:[查询卖家店铺类目属性值]
	 * </p>
	 */
	public List<ItemAttrValueSeller> queryAttrValueSellerList(@Param("sellerAttrId") Long sellerAttrId);

	/**
	 * 
	 * <p>
	 * Discription:[插入item_attribute商品属性]
	 * </p>
	 */
	public void insertItemAttr(@Param("param") ItemAttrBean bean);

	/**
	 * 
	 * <p>
	 * Discription:[插入item_attr商家属性关联表]
	 * </p>
	 */
	public void insertItemAttrSeller(@Param("param") ItemAttrSeller attrSeller);

	/**
	 * 
	 * <p>
	 * Discription:[根据条件查询item_attr商家属性关联表]
	 * </p>
	 * 
	 * @param sellerId      卖家ID
	 * @param shopId        店铺ID
	 * @param cid           类目ID
	 * @param attrId        属性ID
	 */
	public ItemAttrSeller getItemAttrSeller(@Param("sellerId") Long sellerId, @Param("shopId") Long shopId, @Param("cid") Long cid, @Param("attrId") Long attrId);

	/**
	 * 
	 * <p>
	 * Discription:[插入item_attribute_value商品属性值表]
	 * </p>
	 */
	public void insertItemAttrValue(@Param("param") ItemAttrValueBean bean);

	/**
	 * 
	 * <p>
	 * Discription:[插入item_attr_value商家属性值关联表]
	 * </p>
	 */
	public void insertItemAttrValueSeller(@Param("param") ItemAttrValueSeller attrValSeller);

	/**
	 * <p>
	 * Discription:[查询子集类目，包含自己]
	 * </p>
	 */
	public QueryChildCategoryOutDTO queryChildCategory(@Param("entity") ItemCategoryDTO itemCategoryDTO);

	/**
	 * 
	 * <p>
	 * Discription:[根据属性id删除类目属性值关系]
	 * </p>
	 */
	public void deleteCategoryAttrValueByAttrId(@Param("attr_id") Long attr_id);

	/**
	 * 
	 * <p>
	 * Discription:[根据属性id删除属性关联关系]
	 * </p>
	 */
	public void deleteCategoryAttrByAttrId(@Param("attr_id") Long attr_id);

	/**
	 * 
	 * <p>
	 * Discription:[根据类目属性值删除属性值关联关系]
	 * </p>
	 */
	public void deleteCategoryAttrValueByValueId(@Param("value_id") Long value_id);

	/**
	 * @Description: 根据类目的属性id查询属性名
	 */
	public String getAttrNameByAttrId(Long id);

	/**
	 * @Description: 根据类目属性值的id查询属性值的名
	 */
	public String getAttrValueNameByAttrValueId(Long id);

	/**
	 * 根据属性ID 查询 类目ID
	 * @param attrId
	 * @return
	 */
	public List<Long> queryCidByAttributeId(@Param("attrId") Long attrId);

	/**
	 * 根据属性ID 查询 类目ID
	 * @param attrValueId
	 * @return
	 */
	public List<Long> queryCidByAttributeValueId(@Param("attrValueId") Long attrValueId);

}
