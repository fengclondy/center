package cn.htd.promotion.cpc.api;

import java.util.List;
import java.util.Map;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.dto.request.GroupbuyingInfoCmplReqDTO;
import cn.htd.promotion.cpc.dto.request.GroupbuyingInfoReqDTO;
import cn.htd.promotion.cpc.dto.request.SinglePromotionInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.GroupbuyingInfoCmplResDTO;
import cn.htd.promotion.cpc.dto.response.GroupbuyingInfoResDTO;
import cn.htd.promotion.cpc.dto.response.GroupbuyingRecordResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionConfigureDTO;
import cn.htd.promotion.cpc.dto.request.GroupbuyingRecordReqDTO;

public interface GroupbuyingAPI {
	
	/**
	 * 添加团购活动
	 * @param groupbuyingInfoCmplReqDTO
	 * @param messageId
	 * @return
	 */
	public ExecuteResult<?> addGroupbuyingInfo(GroupbuyingInfoCmplReqDTO groupbuyingInfoCmplReqDTO, String messageId);
	
	/**
	 * 修改团购活动
	 * @param groupbuyingInfoCmplReqDTO
	 * @param messageId
	 * @return
	 */
	public ExecuteResult<?> updateGroupbuyingInfo(GroupbuyingInfoCmplReqDTO groupbuyingInfoCmplReqDTO,String messageId);

	/**
	 * 根据promotionId获取的团购活动信息
	 * @param promotionId
	 * @param messageId
	 * @return
	 */
	public ExecuteResult<GroupbuyingInfoCmplResDTO> getGroupbuyingInfoCmplByPromotionId(String promotionId,String messageId);
	
	/**
	 * 根据promotionId获取简单的团购活动信息
	 * @param promotionId
	 * @param messageId
	 * @return
	 */
	public ExecuteResult<GroupbuyingInfoResDTO> getSingleGroupbuyingInfoByPromotionId(String promotionId,String messageId);

	/**
	 * 根据条件分页查询团购活动信息
	 * @param page
	 * @param groupbuyingInfoReqDTO
	 * @param messageId
	 * @return
	 */
	public ExecuteResult<DataGrid<GroupbuyingInfoCmplResDTO>> getGroupbuyingInfoCmplForPage(Pager<GroupbuyingInfoReqDTO> page,
			GroupbuyingInfoReqDTO groupbuyingInfoReqDTO, String messageId);
	
	/**
	 * 根据活动编号时时查询团购真实人数和价格
	 * @param promotionId
	 * @param messageId
	 * @return
	 */
	public ExecuteResult<Map<String, String>> getGBActorCountAndPriceByPromotionId(String promotionId, String messageId);
	
	/**
	 * 根据条件获取单条参团记录
	 * @param groupbuyingRecordReqDTO
	 * @param messageId
	 * @return
	 */
	public ExecuteResult<GroupbuyingRecordResDTO> getSingleGroupbuyingRecord(GroupbuyingRecordReqDTO groupbuyingRecordReqDTO, String messageId);
	
	/**
	 * 分页查询参团记录
	 * @param page
	 * @param groupbuyingRecordReqDTO
	 * @param messageId
	 * @return
	 */
	public ExecuteResult<DataGrid<GroupbuyingRecordResDTO>> geGroupbuyingRecordForPage(Pager<GroupbuyingRecordReqDTO> page,
			GroupbuyingRecordReqDTO groupbuyingRecordReqDTO, String messageId);
	
	/**
	 * 根据promotionId查询活动配置信息
	 * @param promotionId
	 * @param messageId
	 * @return
	 */
	public ExecuteResult<List<PromotionConfigureDTO>> getGBPromotionConfiguresByPromotionId(String promotionId,String messageId);
	
	
	/**
	 * 添加参团记录(供移动端使用)
	 * @param groupbuyingInfoReqDTO
	 * @param messageId
	 * @return
	 */
	ExecuteResult<?> addGroupbuyingRecord2HttpINTFC(GroupbuyingRecordReqDTO dto,String messageId);
	
	/**
	 * 根据promotionId获取的团购活动信息(供移动端使用)
	 * @param promotionId
	 * @param messageId
	 * @return
	 */
	public ExecuteResult<GroupbuyingInfoCmplResDTO> getGroupbuyingInfoCmpl2HttpINTFC(String promotionId,String messageId);

	/**
	 * 根据orgid获取该店铺所有团购商品(供移动端使用)
	 * @param pager
	 * @param dto
	 * @param messageId
	 * @return
	 */
	ExecuteResult<DataGrid<GroupbuyingInfoCmplResDTO>> getGroupbuyingList2HttpINTFC(Pager<GroupbuyingInfoReqDTO> pager, GroupbuyingInfoReqDTO groupbuyingInfoReqDTO, String messageId);
	
	/**
	 * 查询首页单个团购活动(供移动端使用)
	 * @param groupbuyingInfoReqDTO
	 * @param messageId
	 * @return
	 */
	public ExecuteResult<GroupbuyingInfoCmplResDTO> getGroupbuyingHomePage2HttpINTFC(GroupbuyingInfoReqDTO groupbuyingInfoReqDTO, String messageId);
	
	/**
	 * 分页查询我的团购列表(供移动端使用)
	 * @param pager
	 * @param dto
	 * @param messageId
	 * @return
	 */
	ExecuteResult<DataGrid<GroupbuyingInfoCmplResDTO>> getMyGroupbuyingForPage2HttpINTFC(Pager<GroupbuyingInfoReqDTO> pager, GroupbuyingInfoReqDTO groupbuyingInfoReqDTO, String messageId);

	/**
	 * 活动上下架
	 * @param promotionId
	 * @param showStatus
	 * @param messageId
	 * @return
	 */
	ExecuteResult<?> updateShowStatusByPromotionId(SinglePromotionInfoReqDTO singlePromotionInfoReqDTO, String messageId);

	/**
	 * 根据promotionId删除团购活动
	 * @param promotionId
	 * @param messageId
	 * @return
	 */
	ExecuteResult<?> deleteGroupbuyingInfoByPromotionId(GroupbuyingInfoReqDTO groupbuyingInfoReqDTO,String messageId) ;
	
	/**
	 * 商品是否被活动正在使用
	 * @param skuCode
	 * @param messageId
	 * @return
	 */
	public ExecuteResult<Boolean> hasProductIsBeingUsedByPromotion(String skuCode, String messageId);
	
	/**
	 * 修改团购活动-手工更新
	 * @param groupbuyingInfoCmplReqDTO
	 * @param messageId
	 * @return
	 */
	public ExecuteResult<?> updateGroupbuyingInfoByManual(GroupbuyingInfoCmplReqDTO groupbuyingInfoCmplReqDTO,String messageId);
	
	
}
