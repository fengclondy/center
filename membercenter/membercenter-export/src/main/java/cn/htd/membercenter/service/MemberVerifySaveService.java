package cn.htd.membercenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.dto.ApplyBusiRelationDTO;

public interface MemberVerifySaveService {

	/**
	 * 1.保存审核结果，2.更新会员注册状态信息
	 * @param memberId
	 * @return
	 */
	public ExecuteResult<String> saveMemberVerifyInfo(ApplyBusiRelationDTO applyBusiRelationDto);

}
