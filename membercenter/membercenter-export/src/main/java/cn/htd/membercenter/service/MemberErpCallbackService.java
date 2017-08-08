package cn.htd.membercenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.dto.CompanyRelationDownErpCallbackDTO;
import cn.htd.membercenter.dto.MemberDownCallbackDTO;
import cn.htd.membercenter.dto.SalesmanDownErpCallbackDTO;

public interface MemberErpCallbackService {
	/**
	 * 会员下行回调
	 * 
	 * @param dto
	 * @return
	 */
	public ExecuteResult<Boolean> memberDownErpCallback(MemberDownCallbackDTO dto);

	/**
	 * 单位往来关系回调
	 * 
	 * @param dto
	 * @return
	 */
	public ExecuteResult<Boolean> companyRelationDownErpCallback(CompanyRelationDownErpCallbackDTO dto);

	/**
	 * 客商业务员回调
	 * 
	 * @param dto
	 * @return
	 */
	public ExecuteResult<Boolean> salesmanDownErpCallback(SalesmanDownErpCallbackDTO dto);

}
