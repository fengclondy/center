package cn.htd.membercenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.membercenter.dto.SellerMeetingEvaluateDTO;
import cn.htd.membercenter.dto.SellerMeetingSignDTO;

public interface MemberMeetingService {
	/**
	 * 会员会议签到
	 * 
	 * @param dto
	 * @return
	 */
	public ExecuteResult<Boolean> meetingSign(SellerMeetingSignDTO dto);

	/**
	 * 会员会议评价
	 * 
	 * @param dto
	 * @return
	 */
	public ExecuteResult<Boolean> meetingCommand(SellerMeetingEvaluateDTO dto);
}
