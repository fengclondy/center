package cn.htd.membercenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dto.MemberBaseInfoDTO;

public interface MemberSuperBossService {
	/**
	 * 根据客户经理查询会员注册进度列表
	 * 
	 * @param managerCode
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberBaseInfoDTO>> selectMemberByCustmanagerCode(String managerCode, Pager pager);
}
