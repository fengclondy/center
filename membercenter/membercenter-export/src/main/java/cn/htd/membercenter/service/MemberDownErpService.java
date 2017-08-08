package cn.htd.membercenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dto.MemberErpDTO;

public interface MemberDownErpService {
	/**
	 * 查询会员，单位往来，客商业务员下行异常数据
	 * 
	 * @param dto
	 * @param pager
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberErpDTO>> selectErpDownList(MemberErpDTO dto,
			@SuppressWarnings("rawtypes") Pager pager);

	/**
	 * 下行重处理
	 * 
	 * @param dto
	 * @return
	 */
	public ExecuteResult<Boolean> saveErpDownReset(MemberErpDTO dto);
}
