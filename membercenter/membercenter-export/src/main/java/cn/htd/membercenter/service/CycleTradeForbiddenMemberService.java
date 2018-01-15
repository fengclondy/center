package cn.htd.membercenter.service;

import java.util.List;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dto.CycleTradeForbiddenMemberDTO;

/**
 * <Description> 互为上下游禁止交易 <br>
 * 
 * @author xmz<br>
 * @taskId <br>
 * @CreateDate 2018年01月13日 <br>
 */
public interface CycleTradeForbiddenMemberService {

	/**
	 * 新增互为上下游禁止交易会员信息
	 * 
	 * @param dto
	 * @return
	 */
	public ExecuteResult<String> insertCycleTradeForbiddenMember(CycleTradeForbiddenMemberDTO dto);

	/**
	 * 删除互为上下游禁止交易会员信息
	 * 
	 * @param dto
	 * @return
	 */
	public ExecuteResult<String> deleteCycleTradeForbiddenMember(CycleTradeForbiddenMemberDTO dto);
	
	/**
	 * 查询互为上下游禁止交易会员信息(分页)
	 * @param dto
	 * @param pager
	 * @return
	 */
	public ExecuteResult<DataGrid<CycleTradeForbiddenMemberDTO>> selectCycleTradeForbiddenMemberList(
			CycleTradeForbiddenMemberDTO dto, Pager<CycleTradeForbiddenMemberDTO> pager);
	
	/**
	 * 查询该会员是否是互为上下游数据
	 * @param dto
	 * @return
	 */
	public ExecuteResult<Boolean> isCycleTradeForbiddenMember(CycleTradeForbiddenMemberDTO dto);
}
