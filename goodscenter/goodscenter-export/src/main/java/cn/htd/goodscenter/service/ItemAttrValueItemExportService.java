package cn.htd.goodscenter.service;

import java.util.List;

import cn.htd.goodscenter.dto.ItemAttrDTO;
import cn.htd.goodscenter.dto.ItemAttrValueItemDTO;
import cn.htd.common.ExecuteResult;

/**
 * 
 * <p>
 * Description: [属性 属性值 和商品关系]
 * </p>
 */
public interface ItemAttrValueItemExportService {

	/**
	 * 
	 * <p>
	 * Discription:[属性 属性值 和商品关系添加]
	 * </p>
	 */
	public ExecuteResult<String> addItemAttrValueItem(List<ItemAttrValueItemDTO> itemAttrValueItemList);

	/**
	 * 
	 * <p>
	 * Discription:[属性 属性值 和商品关系修改]
	 * </p>
	 */
	public ExecuteResult<String> modifyItemAttrValueItem(List<ItemAttrValueItemDTO> itemAttrValueItemList);

	/**
	 * 
	 * <p>
	 * Discription:[属性 属性值 和商品关系删除]
	 * </p>
	 */
	public ExecuteResult<String> deleteItemAttrValueItem(Long... valueId);

	/**
	 * 
	 * <p>
	 * Discription:[属性 属性值 和商品关系查询]
	 * </p>
	 */
	public ExecuteResult<List<ItemAttrDTO>> queryItemAttrValueItem(ItemAttrValueItemDTO itemAttrValueItemDTO);

}
