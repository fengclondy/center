package com.bjucloud.contentcenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dto.CreditsExchangeDTO;

import java.util.List;

/**
 * <p>
 * Description: [积分兑换商品service]
 * </p>
 */
public interface CreditsExchangeService {

	/**
	 * <p>
	 * Discription:[积分兑换修改]
	 * </p>
	 */
	public ExecuteResult<String> update(CreditsExchangeDTO dto);

	/**
	 * <p>
	 * Discription:[积分兑换分页查询]
	 * </p>
	 */
	public ExecuteResult<DataGrid<CreditsExchangeDTO>> queryCreditExchangeList(CreditsExchangeDTO dto, Pager pager);

	/**
	 * <p>
	 * Discription:[积分兑换单个删除]
	 * </p>
	 */
	public ExecuteResult<String> delete(long id);

	/**
	 * <p>
	 * Discription:[积分兑换添加]
	 * </p>
	 */
	public ExecuteResult<CreditsExchangeDTO> insert(CreditsExchangeDTO dto);

	/**
	 * 根据id获取积分兑换内容
	 */
	public CreditsExchangeDTO selectById(Long id);

	/**
	 * <p>
	 * Discription:[根据物流获取相应的店铺物流信息]
	 * </p>
	 */
	public List<CreditsExchangeDTO> queryCreditsByCredits(CreditsExchangeDTO creditsExchangeDTO);
}
