package cn.htd.promotion.cpc.biz.service;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.biz.dmo.BuyerUseTimelimitedLogDMO;
import cn.htd.promotion.cpc.dto.request.TimelimitedInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.TimelimitedInfoResDTO;

public interface TimelimitedInfoService {

	/**
	 * 添加秒杀活动
	 * @param timelimitedInfoReqDTO
	 * @param messageId
	 */
	public void addTimelimitedInfo(TimelimitedInfoReqDTO timelimitedInfoReqDTO, String messageId);

	/**
	 * 修改秒杀活动
	 * @param timelimitedInfoReqDTO
	 * @param messageId
	 */
	public void updateTimelimitedInfo(TimelimitedInfoReqDTO timelimitedInfoReqDTO, String messageId);

	/**
	 * 根据promotionId获取完整的秒杀活动信息
	 * @param promotionId
	 * @param type 1.数据库商品库存,2.redis商品真实库存 
	 * @param messageId
	 * @return
	 */
	public TimelimitedInfoResDTO getSingleFullTimelimitedInfoByPromotionId(String promotionId,String type,String messageId) ;
	
	/**
	 * 根据promotionId获取简单的秒杀活动信息
	 * @param promotionId
	 * @param messageId
	 * @return
	 */
	public TimelimitedInfoResDTO getSingleTimelimitedInfoByPromotionId(String promotionId,String messageId) ;

	/**
	 * 分页查询秒杀活动信息
	 * @param page
	 * @param timelimitedInfoReqDTO
	 * @param messageId
	 * @return
	 */
	public DataGrid<TimelimitedInfoResDTO> getTimelimitedInfosForPage(Pager<TimelimitedInfoReqDTO> page,
			TimelimitedInfoReqDTO timelimitedInfoReqDTO, String messageId);
	
	/**
	 * 活动上下架
	 * @param promotionId
	 * @param showStatus
	 * @param messageId
	 * @return
	 */
	public String updateShowStatusByPromotionId(TimelimitedInfoReqDTO timelimitedInfoReqDTO, String messageId);
	
	
    /**
     * 异步初始化秒杀活动的Redis数据
     *
     * @param timelimitedInfoResDTO
     */
    public void initTimelimitedInfoRedisInfoWithThread(TimelimitedInfoResDTO timelimitedInfoResDTO);
    
    /**
     * 初始化秒杀活动的Redis数据
     *
     * @param timelimitedInfoResDTO
     */
    public void initTimelimitedInfoRedisInfo(TimelimitedInfoResDTO timelimitedInfoResDTO);

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

	/**
	 * 保存秒杀活动操作记录到redis
	 * 
	 * @param seckillInfoReqDTO
	 */
	public void saveOrUpdateTimelimitedOperlog(BuyerUseTimelimitedLogDMO buyerUseTimelimitedLogDMO);
}
