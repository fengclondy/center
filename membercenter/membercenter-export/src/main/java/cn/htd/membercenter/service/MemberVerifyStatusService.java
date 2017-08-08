package cn.htd.membercenter.service;

import java.util.Date;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.membercenter.dto.MemberVerifyStatusDTO;

public interface MemberVerifyStatusService {
	
	/**
	 * 查询待审核会员列表
	 * @param page
	 * @param verifyStatus:待审核状态为1
	 * @param name：公司名称和法人姓名查询待审核会员类别
	 * @param isDiffIndustry：高级查询——》根据是否异业和时间查询待审核会员列表
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public ExecuteResult<DataGrid<MemberVerifyStatusDTO>> selectByStatus(Pager page,String verifyStatus,Long sellerId,
			String name,String isDiffIndustry, Date startTime, Date endTime);

}
