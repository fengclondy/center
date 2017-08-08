package cn.htd.goodscenter.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.goodscenter.dto.SuperbossProductPushDTO;

public interface SuperbossProductPushService {

	/**
	 * 超级老板商品推送列表查询
	 * 
	 * @param superbossProductPushDTO
	 * @return
	 */
	public ExecuteResult<DataGrid<SuperbossProductPushDTO>> querySuperbossProductPushList(
			SuperbossProductPushDTO superbossProductPushDTO, Pager<SuperbossProductPushDTO> pager);

	/**
	 * 根据skuCode以及recommendClass查询超级老板商品推送信息详情
	 * 
	 * @param superbossProductPushDTO
	 * @return
	 */
	public ExecuteResult<SuperbossProductPushDTO> querySuperbossProductPushInfo(
			SuperbossProductPushDTO superbossProductPushDTO);

	/**
	 * 新增超级老板商品推送信息
	 * 
	 * @param superbossProductPushDTO
	 * @return
	 */
	public ExecuteResult<String> insertSuperbossProductPush(SuperbossProductPushDTO superbossProductPushDTO);

	/**
	 * 编辑超级老板商品推送信息
	 * 
	 * @param superbossProductPushDTO
	 * @return
	 */
	public ExecuteResult<String> updateSuperbossProductPush(SuperbossProductPushDTO superbossProductPushDTO);

	/**
	 * 删除超级老板商品推送信息
	 * 
	 * @param superbossProductPushDTO
	 * @return
	 */
	public ExecuteResult<String> deleteSuperbossProductPush(List<SuperbossProductPushDTO> superbossProductPushDTO);

}
