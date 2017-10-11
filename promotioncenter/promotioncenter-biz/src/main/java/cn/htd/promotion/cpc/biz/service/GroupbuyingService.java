package cn.htd.promotion.cpc.biz.service;

import cn.htd.promotion.cpc.dto.request.GroupbuyingInfoCmplReqDTO;


public interface GroupbuyingService {

	/**
	 * 添加团购活动
	 * @param groupbuyingInfoCmplReqDTO
	 * @param messageId
	 */
	public void addGroupbuyingInfo(GroupbuyingInfoCmplReqDTO groupbuyingInfoCmplReqDTO, String messageId);

	/**
	 * 修改团购活动
	 * @param groupbuyingInfoCmplReqDTO
	 * @param messageId
	 */
	public void updateGroupbuyingInfo(GroupbuyingInfoCmplReqDTO groupbuyingInfoCmplReqDTO, String messageId);

//	/**
//	 * 根据promotionId获取完整的秒杀活动信息
//	 * @param promotionId
//	 * @param type 1.数据库商品库存,2.redis商品真实库存 
//	 * @param messageId
//	 * @return
//	 */
//	public TimelimitedInfoResDTO getSingleFullTimelimitedInfoByPromotionId(String promotionId,String type,String messageId) ;
//	
//	/**
//	 * 根据promotionId获取简单的秒杀活动信息
//	 * @param promotionId
//	 * @param messageId
//	 * @return
//	 */
//	public TimelimitedInfoResDTO getSingleTimelimitedInfoByPromotionId(String promotionId,String messageId) ;
//
//	/**
//	 * 分页查询秒杀活动信息
//	 * @param page
//	 * @param timelimitedInfoReqDTO
//	 * @param messageId
//	 * @return
//	 */
//	public DataGrid<TimelimitedInfoResDTO> getTimelimitedInfosForPage(Pager<TimelimitedInfoReqDTO> page,
//			TimelimitedInfoReqDTO timelimitedInfoReqDTO, String messageId);
//	
//	/**
//	 * 活动上下架
//	 * @param promotionId
//	 * @param showStatus
//	 * @param messageId
//	 * @return
//	 */
//	public String updateShowStatusByPromotionId(TimelimitedInfoReqDTO timelimitedInfoReqDTO, String messageId);
	
	
    /**
     * 异步初始化团购活动的Redis数据
     *
     * @param timelimitedInfoResDTO
     */
    public void initGroupbuyingInfoRedisInfoWithThread(GroupbuyingInfoCmplReqDTO groupbuyingInfoCmplReqDTO);
    
    /**
     * 初始化团购活动的Redis数据
     *
     * @param timelimitedInfoResDTO
     */
    public void initGroupbuyingInfoRedisInfo(GroupbuyingInfoCmplReqDTO groupbuyingInfoCmplReqDTO);

//	/**
//	 * 保存秒杀活动信息到redis
//	 * 
//	 * @param timelimitedInfoResDTO
//	 * @throws Exception
//	 */
//	public void setTimelimitedInfo2Redis(TimelimitedInfoResDTO timelimitedInfoResDTO) throws Exception;
//
//	/**
//	 * 创建秒杀活动时设置秒杀活动锁定库存队列
//	 * 
//	 * @param timelimitedInfoResDTO
//	 * @throws Exception
//	 */
//	public void setTimelimitedReserveQueue(TimelimitedInfoResDTO timelimitedInfoResDTO) throws Exception;
//
//	/**
//	 * 从redis获取秒杀互动信息
//	 * 
//	 * @param promotionId
//	 * @return
//	 * @throws Exception
//	 */
//	public TimelimitedInfoResDTO getTimelimitedInfo(String promotionId) throws Exception;
//
//	/**
//	 * 保存秒杀活动操作记录到redis
//	 * 
//	 * @param seckillInfoReqDTO
//	 */
//	public void saveOrUpdateTimelimitedOperlog(BuyerUseTimelimitedLogDMO buyerUseTimelimitedLogDMO);
//	
//	/**
//	 * 总部秒杀参与会员店列表取得
//	 * 
//	 * @param promotionId 活动ID
//	 * @return 总部秒杀参与会员店列表
//	 */
//	public List<PromotionSellerDetailDTO> getPromotionSellerDetailList(String promotionId);
	
}
