package cn.htd.promotion.cpc.api;

import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.TimelimitedInfoReqDTO;
public interface TimelimitedInfoAPI {
	
	public ExecuteResult<?> addTimelimitedInfo(TimelimitedInfoReqDTO timelimitedInfoReqDTO,String messageId);
	
	public ExecuteResult<?> updateTimelimitedInfo(TimelimitedInfoReqDTO timelimitedInfoReqDTO,String messageId);

	
}
