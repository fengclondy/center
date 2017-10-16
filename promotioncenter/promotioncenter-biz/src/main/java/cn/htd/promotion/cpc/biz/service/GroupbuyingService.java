package cn.htd.promotion.cpc.biz.service;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.dto.request.GroupbuyingInfoCmplReqDTO;
import cn.htd.promotion.cpc.dto.request.GroupbuyingInfoReqDTO;
import cn.htd.promotion.cpc.dto.request.GroupbuyingRecordReqDTO;
import cn.htd.promotion.cpc.dto.response.GroupbuyingInfoCmplResDTO;
import cn.htd.promotion.cpc.dto.response.GroupbuyingInfoResDTO;
import cn.htd.promotion.cpc.dto.response.GroupbuyingRecordResDTO;


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

	/**
	 * 根据promotionId获取的团购活动信息
	 * @param promotionId
	 * @param messageId
	 * @return
	 */
	public GroupbuyingInfoCmplResDTO getGroupbuyingInfoCmplByPromotionId(String promotionId,String messageId) ;
	
	/**
	 * 根据promotionId获取简单的团购活动信息
	 * @param promotionId
	 * @param messageId
	 * @return
	 */
	public GroupbuyingInfoResDTO getSingleGroupbuyingInfoByPromotionId(String promotionId,String messageId) ;

	/**
	 * 分页查询团购活动信息
	 * @param page
	 * @param groupbuyingInfoReqDTO
	 * @param messageId
	 * @return
	 */
	public DataGrid<GroupbuyingInfoCmplResDTO> getGroupbuyingInfoCmplForPage(Pager<GroupbuyingInfoReqDTO> page,
			GroupbuyingInfoReqDTO groupbuyingInfoReqDTO, String messageId);
	
	/**
	 * 添加参团记录
	 * @param dto
	 * @param messageId
	 */
	void addGroupbuyingRecord2HttpINTFC(GroupbuyingRecordReqDTO groupbuyingRecordReqDTO, String messageId);
	
	/**
	 * 根据条件获取单条参团记录
	 * @param dto
	 * @param messageId
	 * @return
	 */
	public GroupbuyingRecordResDTO getSingleGroupbuyingRecord(GroupbuyingRecordReqDTO groupbuyingRecordReqDTO, String messageId);
	
	/**
	 * 分页查询参团记录
	 * @param page
	 * @param groupbuyingInfoReqDTO
	 * @param messageId
	 * @return
	 */
	public DataGrid<GroupbuyingRecordResDTO> geGroupbuyingRecordForPage(Pager<GroupbuyingRecordReqDTO> page,
			GroupbuyingRecordReqDTO groupbuyingRecordReqDTO, String messageId);


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
     * @param groupbuyingInfoCmplReqDTO
     */
    public void initGroupbuyingInfoRedisInfoWithThread(String promotionId);
    
    /**
     * 初始化团购活动的Redis数据
     *
     * @param groupbuyingInfoCmplReqDTO
     */
    public void initGroupbuyingInfoRedisInfo(GroupbuyingInfoCmplResDTO groupbuyingInfoCmplResDTO);

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
