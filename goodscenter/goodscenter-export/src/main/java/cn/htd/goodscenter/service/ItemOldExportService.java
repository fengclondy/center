package cn.htd.goodscenter.service;

import cn.htd.goodscenter.dto.ItemOldDTO;
import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.dto.indto.ItemOldInDTO;
import cn.htd.goodscenter.dto.indto.ItemOldSeachInDTO;
import cn.htd.goodscenter.dto.outdto.ItemOldOutDTO;
import cn.htd.goodscenter.dto.outdto.ItemOldSeachOutDTO;

/**
 * 
 * <p>
 * Description: [二手市场]
 * </p>
 */
public interface ItemOldExportService {

	/**
	 * 
	 * <p>
	 * Discription:[二手商品添加]
	 * </p>
	 */
	public ExecuteResult<String> addItemOld(ItemOldInDTO itemOldInDTO);

	/**
	 * 
	 * <p>
	 * Discription:[二手商品修改]
	 * </p>
	 */
	public ExecuteResult<String> modifyItemOld(ItemOldInDTO itemOldInDTO);

	/**
	 * 
	 * <p>
	 * Discription:[查询二手商品列表]
	 * </p>
	 */
	public ExecuteResult<DataGrid<ItemOldDTO>> queryItemOld(ItemOldDTO itemOldDTO, Pager page);

	/**
	 * 
	 * <p>
	 * Discription:[根据ID查询]
	 * </p>
	 */
	public ExecuteResult<ItemOldOutDTO> getItemOld(Long itemId);

	/**
	 * 
	 * <p>
	 * Discription:[修改状态 20审核驳回 3待上架 4在售(上架) 5已下架 8删除 （可批量）]
	 * </p>
	 */
	public ExecuteResult<String> modifyItemOldStatus(String comment, String platformUserId, Long status, Long... itemId);

	/**
	 * 
	 * <p>
	 * Discription:[二手商品搜索]
	 * </p>
	 */
	public ExecuteResult<ItemOldSeachOutDTO> seachItemOld(ItemOldSeachInDTO itemOldSeachInDTO, Pager page);
}
