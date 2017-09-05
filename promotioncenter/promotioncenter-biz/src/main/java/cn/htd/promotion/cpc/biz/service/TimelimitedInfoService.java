package cn.htd.promotion.cpc.biz.service;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.dto.request.TimelimitedInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;

public interface TimelimitedInfoService {
	
	public void addTimelimitedInfo(TimelimitedInfoReqDTO timelimitedInfoReqDTO,String messageId);
	
	public void updateTimelimitedInfo(TimelimitedInfoReqDTO timelimitedInfoReqDTO,String messageId);
	
	public TimelimitedInfoResDTO getSingleTimelimitedInfo(TimelimitedInfoReqDTO timelimitedInfoReqDTO,String messageId);

	public DataGrid<TimelimitedInfoResDTO> getTimelimitedInfosForPage(Pager<TimelimitedInfoReqDTO> page,TimelimitedInfoReqDTO timelimitedInfoReqDTO, String messageId);
}
