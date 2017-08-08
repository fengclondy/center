package cn.htd.membercenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.dto.MemberInvoiceDTO;

public interface MemberInvoiceService {

	/**
	 * 根据会员编码查询会员发票信息
	 * 
	 * @param memberBaseDTO
	 * @return
	 */
	public ExecuteResult<MemberInvoiceDTO> queryMemberInvoiceInfo(MemberInvoiceDTO memberInvoiceDTO);

	/**
	 * 修改会员发票信息
	 * 
	 * @param memberInvoiceDTO
	 * @return
	 */
	public ExecuteResult<Boolean> modifyMemberInoviceInfo(MemberInvoiceDTO memberInvoiceDTO);

}
