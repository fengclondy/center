package cn.htd.goodscenter.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.htd.goodscenter.dto.CatAttrSellerDTO;
import cn.htd.goodscenter.dto.ItemDTO;
import cn.htd.goodscenter.dto.indto.QueryItemCategoryInDTO;
import cn.htd.goodscenter.dto.outdto.QueryChildCategoryOutDTO;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.domain.ItemCategoryCascade;
import cn.htd.goodscenter.dto.CategoryAttrDTO;
import cn.htd.goodscenter.dto.ItemAttrDTO;
import cn.htd.goodscenter.dto.ItemAttrValueDTO;
import cn.htd.goodscenter.dto.ItemCatCascadeDTO;
import cn.htd.goodscenter.dto.ItemCategoryDTO;

/**
 * 品类接口
 */
public interface ItemCategoryService {
	/**
	 * 平台类目添加功能接口
	 */
	public ExecuteResult<String> addItemCategory(ItemCategoryDTO itemCategoryDTO);

	/**
	 * <p>
	 * Discription:[删除商品类目]
	 * </p>
	 * @param itemCategoryDTO
	 */
	public ExecuteResult<String> deleteItemCategory(ItemCategoryDTO itemCategoryDTO);

	/**
	 * 平台所有类目列表查询
	 */
	public ExecuteResult<DataGrid<ItemCategoryDTO>> queryItemCategoryAllList(Pager Pager);

	/**
	 * 根据父级id查询平台类目类目列表
	 * 根据一级目录查二级目录，根据二级目录查三级目录
	 */
	public ExecuteResult<DataGrid<ItemCategoryDTO>> queryItemCategoryList(Long parentCid);

	/**
	 * 查询类目树
	 * @return
	 */
	public ExecuteResult<List<ItemCatCascadeDTO>> queryItemCategoryTree();


	/**
	 * 查询类目树 带条件
	 * @return
	 */
	public ExecuteResult<List<ItemCatCascadeDTO>> queryItemCategoryTreeByThirdCid(List<Long> thirdCidList);

	/**
	 * 根据三级类目查询一级二级三级类目，按照分隔符组装
	 * 例：一级类目>>二级类目>>三级类目
	 *   KEY :　VALUE
	 * 	 firstCategoryId : 一级类目ID
	     firstCategoryName : 一级类目名称
	     secondCategoryId : 二级类目ID
	     secondCategoryName : 二级类目名称
	     thirdCategoryId : 三级类目ID
	     thirdCategoryName : 三级类目名称
	 * @param cid
	 * @param separator
	 * @return
	 */
	public ExecuteResult<Map<String, Object>> queryItemOneTwoThreeCategoryName(Long cid, String separator);

	/**
	 * 查询所有的三级类目，根据U一级、二级或者三级其中一个
	 * @param firstCategoryId
	 * @param secondCategoryId
	 * @param thirdCategoryId
	 * @return
	 */
	public Long[] getAllThirdCategoryByCategoryId(Long firstCategoryId, Long secondCategoryId, Long thirdCategoryId);

	/**
	 * 根据父级类目查询所有它的三级类目
	 * @param parentCid
	 * @return
	 */
	public ExecuteResult<List<ItemCategoryDTO>> queryThirdCategoryByParentId(Long parentCid);

	/**
	 * <p>
	 * Discription:[根据类目级别查询对应级别的所有类目]
	 * </p>
	 */
	public DataGrid<ItemCategoryDTO> queryItemByCategoryLev(Integer categoryLev);

	/**
	 * <p>
	 * Discription:[根据lev层级及第二/三级类目ID查询父级类目ID的信息]
	 * </p>
	 */
	public ExecuteResult<List<ItemCatCascadeDTO>> queryParentCategoryList(Integer lev, Long... cid);

	/**
	 * <p>
	 * Discription:[只根据三级类目查询一级类目]
	 * </p>
	 */
	public ExecuteResult<List<ItemCatCascadeDTO>> queryParentCategoryList(Long... cid);

	/**
	 * 平台类目属性添加
	 * @param cid
	 * @param attrName
	 * @param attrType   属性类型:1:销售属性;2:商品属性
	 */
	public ExecuteResult<Long> addCategoryAttr(Long cid, String attrName, Integer attrType, Integer sortNumber, Long createId, String createName);

	/**
	 * 平台类目类目属性查询
	 * @param cid 品类Id
	 * @param attrType  属性类型:1:销售属性;2:商品属性
	 */
	public ExecuteResult<DataGrid<CategoryAttrDTO>> queryCategoryAttrList(Long cid, Integer attrType);

	/**
	 * 平台类目属性值添加
	 */
	public ExecuteResult<Long> addCategoryAttrValue(Long cid, Long attrId, String valueName, Integer sortNumber, Long createId, String createName);

	/**
	 * <p>
	 * Discription:[查询卖家属性查询]
	 * </p>
	 */
	public ExecuteResult<List<ItemAttrDTO>> queryCatAttrSellerList(CatAttrSellerDTO inDTO);

	/**
	 * 
	 * <p>
	 * Discription:[ 根据属性键值对查询属性列表 ]
	 * </p>
	 */
	public ExecuteResult<List<ItemAttrDTO>> queryCatAttrByKeyVals(String attrStr);

	/**
	 * 
	 * <p>
	 * Discription:[增加卖家商品类目属性]
	 * </p>
	 */
	public ExecuteResult<ItemAttrDTO> addItemAttrSeller(CatAttrSellerDTO inDTO);

	/**
	 * 
	 * <p>
	 * Discription:[增加卖家商品类目属性值]
	 * </p>
	 */
	public ExecuteResult<ItemAttrValueDTO> addItemAttrValueSeller(CatAttrSellerDTO inDTO);

	/**
	 * <p>
	 * Discription:[修改类目]
	 * </p>
	 */
	public ExecuteResult<String> updateCategory(ItemCategoryDTO itemCategoryDTO);

	/**
	 * 
	 * <p>
	 * Discription:[删除类目属性关联关系]
	 * </p>
	 */
	public ExecuteResult<String> deleteCategoryAttr(Long cid, Long attr_id, Integer attrType);

	/**
	 * 
	 * <p>
	 * Discription:[删除类目属性对应的属性值]
	 * </p>
	 */
	public ExecuteResult<String> deleteCategoryAttrValue(Long cid, Long attr_id, Long value_id, Integer attrType);

	/**
	 * 根据cid查询类目
	 */
	public ExecuteResult<ItemCategoryDTO> getCategoryByCid(Long cid);

	/**
	 * 批量查询类目
	 */
	public ExecuteResult<List<ItemCategoryDTO>> getCategoryListByCids(List<Long> cidList);

	/**
	 * <p>
	 * Discription:[根据cid和sellerId查询商品]
	 * </p>
	 */
	public List<ItemDTO> getItemByCid(ItemDTO itemDTO);

	/**
	 * @Description: 根据属性Id的到属性明 类目和商品通用
	 */
	public String getAttrNameByAttrId(Long id);

	/**
	 * 
	 * @Description: 根据属性值id得到属性名类目和商品通用
	 */
	public String getAttrValueNameByAttrValueId(Long id);

	/**
	 * 用于搜索引擎同步数据
	 * */
	public List<ItemCategoryCascade> queryCategoryBySyncTime(Date syncTime);

	/**
	 * <p>
	 * Discription:[根据属性ID组查询属性]
	 * </p>
	 */
	public List<ItemAttrDTO> queryItemAttrList(List<String> attrIds);

	/**
	 * <p>
	 * Discription:[根据商品属性ID，和商品属性值ID查询对应属性信息]
	 * </p>
	 * @param key     商品属性ID
	 * @param value   商品属性值ID 集合 若此参数为空 则查询商品属性ID下的所有属性
	 */
	public List<ItemAttrValueDTO> queryItemAttrValueList(Long key,List<String> value);

	/**
	 * 导入类目属性和属性值
	 */
	public void importCategoryAttribute();

	/**
	 * 根据时间戳查询品类类别，提供超级老板
	 */
	public ExecuteResult<DataGrid<ItemCategoryDTO>> queryItemCategoryList4SuperBoss(QueryItemCategoryInDTO queryItemCategoryInDTO, Pager Pager);
}
