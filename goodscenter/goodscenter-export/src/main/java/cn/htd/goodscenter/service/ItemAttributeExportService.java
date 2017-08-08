package cn.htd.goodscenter.service;

import java.util.List;

import cn.htd.goodscenter.dto.CatAttrSellerDTO;
import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.dto.ItemAttrDTO;
import cn.htd.goodscenter.dto.ItemAttrValueDTO;

/**
 * 
 * <p>
 * Description: [商品属性服务接口]
 * </p>
 */
public interface ItemAttributeExportService {

	/**
	 * 
	 * <p>
	 * Discription:[添加商品属性]
	 * </p>
	 */
	public ExecuteResult<ItemAttrDTO> addItemAttribute(ItemAttrDTO itemAttr);

	/**
	 * 
	 * <p>
	 * Discription:[删除商品属性]
	 * </p>
	 */
	public ExecuteResult<ItemAttrDTO> deleteItemAttribute(ItemAttrDTO itemAttr);

	/**
	 * 
	 * <p>
	 * Discription:[修改商品属性]
	 * </p>
	 */
	public ExecuteResult<ItemAttrDTO> modifyItemAttribute(ItemAttrDTO itemAttr);

	/**
	 * 
	 * <p>
	 * Discription:[添加商品属性值]
	 * </p>
	 */
	public ExecuteResult<ItemAttrValueDTO> addItemAttrValue(ItemAttrValueDTO itemAttrValue);

	/**
	 * 
	 * <p>
	 * Discription:[删除商品属性值]
	 * </p>
	 */
	public ExecuteResult<ItemAttrValueDTO> deleteItemAttrValue(ItemAttrValueDTO itemAttrValue);

	/**
	 * 
	 * <p>
	 * Discription:[修改商品属性值]
	 * </p>
	 */
	public ExecuteResult<ItemAttrValueDTO> modifyItemAttrValue(ItemAttrValueDTO itemAttrValue);

	/**
	 * 
	 * <p>
	 * Discription:[复制属性 属性值 然后返回]
	 * </p>
	 * 
	 * @param inDTO
	 *            sellerId 卖家ID 必填 ;
	 *            shopId 商家ID 必填 ; 
	 *            cid 平台类目ID 必填; 
	 *            attrType 属性类型:1:销售属性;2:非销售属性 必填;
	 * @param operator
	 *            1 商家 2 平台
	 * 
	 */
	public ExecuteResult<List<ItemAttrDTO>> addItemAttrValueBack(CatAttrSellerDTO inDTO, int operator);

	public ExecuteResult<String> deleteByAttrId(Long attrId);

	public ExecuteResult<String> delete(Long id);

	public ExecuteResult<String> deleteItemAttrByAttrId(Long attrId,Long cid,Long shopId);

	public ExecuteResult<String> deleteAttrValueById(Long id);

	/**
	 *
	 * <p>
	 * Discription:[根据属性ID查找属性值]
	 * </p>
	 */
	public ExecuteResult<List<ItemAttrValueDTO>> queryByAttrId(Long attrId);


}
