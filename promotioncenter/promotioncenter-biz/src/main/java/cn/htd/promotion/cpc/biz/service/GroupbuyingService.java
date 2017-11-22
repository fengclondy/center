package cn.htd.promotion.cpc.biz.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.dto.request.GroupbuyingInfoCmplReqDTO;
import cn.htd.promotion.cpc.dto.request.GroupbuyingInfoReqDTO;
import cn.htd.promotion.cpc.dto.request.GroupbuyingRecordReqDTO;
import cn.htd.promotion.cpc.dto.request.SinglePromotionInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.GroupbuyingInfoCmplResDTO;
import cn.htd.promotion.cpc.dto.response.GroupbuyingInfoResDTO;
import cn.htd.promotion.cpc.dto.response.GroupbuyingRecordResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionConfigureDTO;


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
	 * 根据promotionId查询活动配置信息
	 * @param promotionId
	 * @param messageId
	 * @return
	 */
	public List<PromotionConfigureDTO> getGBPromotionConfiguresByPromotionId(String promotionId,String messageId) ;
	
	/**
	 * 添加参团记录
	 * @param dto
	 * @param messageId
	 */
	String addGroupbuyingRecord2HttpINTFC(GroupbuyingRecordReqDTO groupbuyingRecordReqDTO, String messageId);
	
	/**
	 * 根据活动编号时时查询团购真实人数和价格
	 * @param promotionId
	 * @param messageId
	 * @return
	 */
	Map<String, String> getGBActorCountAndPriceByPromotionId(String promotionId, String messageId);
	
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


	/**
	 * 活动上下架
	 * @param promotionId
	 * @param showStatus
	 * @param messageId
	 * @return
	 */
	public String updateShowStatusByPromotionId(SinglePromotionInfoReqDTO singlePromotionInfoReqDTO, String messageId);
	
	/**
	 * 根据promotionId删除团购活动
	 * @param promotionId
	 * @param messageId
	 * @return
	 */
	public String deleteGroupbuyingInfoByPromotionId(GroupbuyingInfoReqDTO groupbuyingInfoReqDTO,String messageId) ;
	
	/**
	 * 商品是否被活动正在使用
	 * @param skuCode
	 * @param messageId
	 * @return
	 */
	public Boolean hasProductIsBeingUsedByPromotion(String skuCode,Date startTime,Date endTime, String messageId);
	
	
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

	/**
	 * 根据orgId 查询店铺所有的团购商品(供移动端使用)
	 * @param page
	 * @param groupbuyingInfoReqDTO
	 * @param messageId
	 * @return
	 */
	DataGrid<GroupbuyingInfoCmplResDTO> getGroupbuyingInfo4MobileForPage(Pager<GroupbuyingInfoReqDTO> page, GroupbuyingInfoReqDTO groupbuyingInfoReqDTO, String messageId);
	
	/**
	 * 根据promotionId获取的团购活动信息(供移动端使用)
	 * @param promotionId
	 * @param messageId
	 * @return
	 */
	public GroupbuyingInfoCmplResDTO getGroupbuyingInfoCmplByPromotionId4Mobile(String promotionId,String messageId) ;
	
	/**
	 * 查询首页单个团购活动(供移动端使用)
	 * @return
	 */
	GroupbuyingInfoCmplResDTO getGroupbuyingInfo4MobileHomePage(GroupbuyingInfoReqDTO groupbuyingInfoReqDTO, String messageId);
	
	/**
	 * 分页查询我的团购列表(供移动端使用)
	 * @param page
	 * @param groupbuyingInfoReqDTO
	 * @param messageId
	 * @return
	 */
	DataGrid<GroupbuyingInfoCmplResDTO> getMyGroupbuying4MobileForPage(Pager<GroupbuyingInfoReqDTO> page, GroupbuyingInfoReqDTO groupbuyingInfoReqDTO, String messageId);
	
	
	/**
	 * 修改团购活动-手工更新
	 * @param groupbuyingInfoCmplReqDTO
	 * @param messageId
	 */
	public void updateGroupbuyingInfoByManual(GroupbuyingInfoCmplReqDTO groupbuyingInfoCmplReqDTO, String messageId);
	
	
}
