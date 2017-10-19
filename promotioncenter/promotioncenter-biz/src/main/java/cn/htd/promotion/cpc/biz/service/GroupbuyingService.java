package cn.htd.promotion.cpc.biz.service;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.dto.request.GroupbuyingInfoCmplReqDTO;
import cn.htd.promotion.cpc.dto.request.GroupbuyingInfoReqDTO;
import cn.htd.promotion.cpc.dto.request.GroupbuyingRecordReqDTO;
import cn.htd.promotion.cpc.dto.request.SinglePromotionInfoReqDTO;
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
	public void deleteGroupbuyingInfoByPromotionId(GroupbuyingInfoReqDTO groupbuyingInfoReqDTO,String messageId) ;
	
	
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
}
