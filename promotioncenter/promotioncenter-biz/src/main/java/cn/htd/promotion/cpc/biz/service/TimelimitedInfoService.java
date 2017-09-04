package cn.htd.promotion.cpc.biz.service;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.dto.request.TimelimitedInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;

public interface TimelimitedInfoService {

	public void addTimelimitedInfo(TimelimitedInfoReqDTO timelimitedInfoReqDTO, String messageId);

	public void updateTimelimitedInfo(TimelimitedInfoReqDTO timelimitedInfoReqDTO, String messageId);

	public TimelimitedInfoResDTO getSingleTimelimitedInfo(TimelimitedInfoReqDTO timelimitedInfoReqDTO,
			String messageId);

	public DataGrid<TimelimitedInfoResDTO> getTimelimitedInfosForPage(Pager<TimelimitedInfoReqDTO> page,
			TimelimitedInfoReqDTO timelimitedInfoReqDTO, String messageId);

	/**
	 * 保存秒杀活动信息到redis
	 * 
	 * @param timelimitedInfoResDTO
	 * @throws Exception
	 */
	public void setTimelimitedInfo2Redis(TimelimitedInfoResDTO timelimitedInfoResDTO) throws Exception;

	/**
	 * 创建秒杀活动时设置秒杀活动锁定库存队列
	 * 
	 * @param timelimitedInfoResDTO
	 * @throws Exception
	 */
	public void setTimelimitedReserveQueue(TimelimitedInfoResDTO timelimitedInfoResDTO) throws Exception;

	/**
	 * 从redis获取秒杀互动信息
	 * 
	 * @param promotionId
	 * @return
	 * @throws Exception
	 */
	public TimelimitedInfoResDTO getTimelimitedInfo(String promotionId) throws Exception;
}
