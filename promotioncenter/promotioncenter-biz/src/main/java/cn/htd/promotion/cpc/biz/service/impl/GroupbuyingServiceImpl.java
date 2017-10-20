package cn.htd.promotion.cpc.biz.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.biz.dao.GroupbuyingInfoDAO;
import cn.htd.promotion.cpc.biz.dao.GroupbuyingPriceSettingDAO;
import cn.htd.promotion.cpc.biz.dao.GroupbuyingRecordDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionConfigureDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionInfoDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionStatusHistoryDAO;
import cn.htd.promotion.cpc.biz.dao.SinglePromotionInfoDAO;
import cn.htd.promotion.cpc.biz.dmo.PromotionInfoDMO;
import cn.htd.promotion.cpc.biz.handle.PromotionGroupbuyingRedisHandle;
import cn.htd.promotion.cpc.biz.service.GroupbuyingService;
import cn.htd.promotion.cpc.common.constants.GroupbuyingConstants;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.constants.TimelimitedConstants;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.emums.YesNoEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.GeneratorUtils;
import cn.htd.promotion.cpc.common.util.KeyGeneratorUtils;
import cn.htd.promotion.cpc.dto.request.GroupbuyingInfoCmplReqDTO;
import cn.htd.promotion.cpc.dto.request.GroupbuyingInfoReqDTO;
import cn.htd.promotion.cpc.dto.request.GroupbuyingPriceSettingReqDTO;
import cn.htd.promotion.cpc.dto.request.GroupbuyingRecordReqDTO;
import cn.htd.promotion.cpc.dto.request.SinglePromotionInfoCmplReqDTO;
import cn.htd.promotion.cpc.dto.request.SinglePromotionInfoReqDTO;
import cn.htd.promotion.cpc.dto.response.GroupbuyingInfoCmplResDTO;
import cn.htd.promotion.cpc.dto.response.GroupbuyingInfoResDTO;
import cn.htd.promotion.cpc.dto.response.GroupbuyingPriceSettingResDTO;
import cn.htd.promotion.cpc.dto.response.GroupbuyingRecordResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionConfigureDTO;
import cn.htd.promotion.cpc.dto.response.PromotionStatusHistoryDTO;
import cn.htd.promotion.cpc.dto.response.SinglePromotionInfoResDTO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


@Service("groupbuyingService")
public class GroupbuyingServiceImpl implements GroupbuyingService {

    private static final Logger logger = LoggerFactory.getLogger(GroupbuyingServiceImpl.class);

    @Resource
    private GroupbuyingInfoDAO groupbuyingInfoDAO;

    @Resource
    private GroupbuyingPriceSettingDAO groupbuyingPriceSettingDAO;

    @Resource
    private GroupbuyingRecordDAO groupbuyingRecordDAO;

    @Resource
    private SinglePromotionInfoDAO singlePromotionInfoDAO;
    
    @Resource
    private PromotionConfigureDAO promotionConfigureDAO;

    @Resource
    private DictionaryUtils dictionary;
    
    @Resource
    private GeneratorUtils noGenerator;

    @Resource
    private PromotionStatusHistoryDAO promotionStatusHistoryDAO;
    
    @Resource
	private PromotionGroupbuyingRedisHandle promotionGroupbuyingRedisHandle;
    
    @Resource
    private PromotionInfoDAO promotionInfoDAO;
    
    @Resource
    private KeyGeneratorUtils keyGeneratorUtils;
    

    @Override
    public void addGroupbuyingInfo(GroupbuyingInfoCmplReqDTO groupbuyingInfoCmplReqDTO, String messageId) {

        // 当前时间
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();

        try {

        	SinglePromotionInfoReqDTO singlePromotionInfoReqDTO = groupbuyingInfoCmplReqDTO.getSinglePromotionInfoReqDTO();
        	
        	String promotionType = singlePromotionInfoReqDTO.getPromotionType();
        	String promotionId = noGenerator.generateGroupbuyingPromotionId(promotionType);
        	
        	// 添加活动信息
        	singlePromotionInfoReqDTO.setPromotionId(promotionId);
        	singlePromotionInfoReqDTO.setIsVip(1);//是VIP
        	setPromotionStatusInfo(singlePromotionInfoReqDTO);
        	singlePromotionInfoReqDTO.setCreateId(groupbuyingInfoCmplReqDTO.getCreateId());
        	singlePromotionInfoReqDTO.setCreateName(groupbuyingInfoCmplReqDTO.getCreateName());
        	singlePromotionInfoReqDTO.setCreateTime(currentTime);
        	singlePromotionInfoReqDTO.setModifyId(groupbuyingInfoCmplReqDTO.getModifyId());
        	singlePromotionInfoReqDTO.setModifyName(groupbuyingInfoCmplReqDTO.getModifyName());
        	singlePromotionInfoReqDTO.setModifyTime(currentTime);
        	int singlePromotionInfoRet = singlePromotionInfoDAO.addPromotionInfo(singlePromotionInfoReqDTO);
        	if(1 != singlePromotionInfoRet){
        		throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_NOT_EXIST.getCode(), "新建促销活动失败！");
        	}
        	
        	// 添加配置信息
        	SinglePromotionInfoCmplReqDTO singlePromotionInfoCmplReqDTO = (SinglePromotionInfoCmplReqDTO) singlePromotionInfoReqDTO;
            List<PromotionConfigureDTO> pclist = singlePromotionInfoCmplReqDTO.getPromotionConfigureList();
            if (pclist != null && pclist.size() > 0) {
                for (PromotionConfigureDTO pcd : pclist) {
                    pcd.setPromotionId(promotionId);
                    pcd.setCreateId(groupbuyingInfoCmplReqDTO.getCreateId());
                    pcd.setCreateName(groupbuyingInfoCmplReqDTO.getCreateName());
                    pcd.setDeleteFlag(YesNoEnum.NO.getValue());
                    promotionConfigureDAO.add(pcd);
                }
            }
        	
        	// 添加团购活动信息
        	groupbuyingInfoCmplReqDTO.setPromotionId(promotionId);
        	groupbuyingInfoCmplReqDTO.setDeleteFlag(Boolean.FALSE);
        	groupbuyingInfoCmplReqDTO.setCreateTime(currentTime);
        	groupbuyingInfoCmplReqDTO.setModifyTime(currentTime);
        	int groupbuyingInfoRet = groupbuyingInfoDAO.insert(groupbuyingInfoCmplReqDTO);
        	if(1 != groupbuyingInfoRet){
        		throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_NOT_EXIST.getCode(), "新建团购促销活动失败！");
        	}
        	

        	
        	// 添加团购价格设置信息
        	List<GroupbuyingPriceSettingReqDTO> groupbuyingPriceSettingReqDTOList = groupbuyingInfoCmplReqDTO.getGroupbuyingPriceSettingReqDTOList();
        	if(null != groupbuyingPriceSettingReqDTOList && groupbuyingPriceSettingReqDTOList.size() > 0){
            	for(GroupbuyingPriceSettingReqDTO groupbuyingPriceSettingReqDTO : groupbuyingPriceSettingReqDTOList){
                	groupbuyingPriceSettingReqDTO.setPromotionId(promotionId);
            		groupbuyingPriceSettingReqDTO.setItemId(groupbuyingInfoCmplReqDTO.getItemId());
            		groupbuyingPriceSettingReqDTO.setSkuCode(groupbuyingInfoCmplReqDTO.getSkuCode());
            		groupbuyingPriceSettingReqDTO.setDeleteFlag(Boolean.FALSE);
            		groupbuyingPriceSettingReqDTO.setCreateId(groupbuyingInfoCmplReqDTO.getCreateId());
            		groupbuyingPriceSettingReqDTO.setCreateName(groupbuyingInfoCmplReqDTO.getCreateName());
            		groupbuyingPriceSettingReqDTO.setCreateTime(currentTime);
            		groupbuyingPriceSettingReqDTO.setModifyId(groupbuyingInfoCmplReqDTO.getModifyId());
            		groupbuyingPriceSettingReqDTO.setModifyName(groupbuyingInfoCmplReqDTO.getModifyName());
                	groupbuyingPriceSettingReqDTO.setModifyTime(currentTime);
                	groupbuyingPriceSettingDAO.insert(groupbuyingPriceSettingReqDTO);
            	}
        	}

        	

            // 添加团购活动履历
            addPromotionStatusHistory(singlePromotionInfoReqDTO);

            // 异步初始化团购活动的Redis数据
            initGroupbuyingInfoRedisInfoWithThread(promotionId);

        } catch (Exception e) {
            logger.error("messageId{}:执行方法【addGroupbuyingInfo】报错：{}", messageId, e.toString());
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateGroupbuyingInfo(GroupbuyingInfoCmplReqDTO groupbuyingInfoCmplReqDTO, String messageId) {

        try {

        	String promotionId = groupbuyingInfoCmplReqDTO.getPromotionId();
        	GroupbuyingInfoResDTO groupbuyingInfoRes_check = groupbuyingInfoDAO.selectByPromotionId(promotionId);
            if (null == groupbuyingInfoRes_check) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.NORESULT.getCode(), "团购促销活动不存在！");
            }

            Calendar calendar = Calendar.getInstance();
            Date currentTime = calendar.getTime();

           
        	SinglePromotionInfoReqDTO singlePromotionInfoReqDTO = groupbuyingInfoCmplReqDTO.getSinglePromotionInfoReqDTO();
        	
        	// 修改活动信息
        	singlePromotionInfoReqDTO.setPromotionId(promotionId);
        	singlePromotionInfoReqDTO.setIsVip(1);//是VIP
        	setPromotionStatusInfo(singlePromotionInfoReqDTO);
        	singlePromotionInfoReqDTO.setModifyId(groupbuyingInfoCmplReqDTO.getModifyId());
        	singlePromotionInfoReqDTO.setModifyName(groupbuyingInfoCmplReqDTO.getModifyName());
        	singlePromotionInfoReqDTO.setModifyTime(currentTime);
        	int singlePromotionInfoRet = singlePromotionInfoDAO.updatePromotionInfo(singlePromotionInfoReqDTO);
        	if(1 != singlePromotionInfoRet){
        		throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_NOT_EXIST.getCode(), "修改促销活动失败！");
        	}
        	
        	// 添加配置信息 (先伪删除所有活动的配置信息，然后再添加)
        	SinglePromotionInfoCmplReqDTO singlePromotionInfoCmplReqDTO = (SinglePromotionInfoCmplReqDTO) singlePromotionInfoReqDTO;
            List<PromotionConfigureDTO> pclist = singlePromotionInfoCmplReqDTO.getPromotionConfigureList();
            if (pclist != null && pclist.size() > 0) {
            	// 删除所有活动的配置信息
            	promotionConfigureDAO.deleteByPromotionId(promotionId);
            	
                for (PromotionConfigureDTO pcd : pclist) {
                    pcd.setPromotionId(groupbuyingInfoCmplReqDTO.getPromotionId());
                    pcd.setCreateId(groupbuyingInfoCmplReqDTO.getModifyId());
                    pcd.setCreateName(groupbuyingInfoCmplReqDTO.getModifyName());
                    pcd.setDeleteFlag(YesNoEnum.NO.getValue());
                    promotionConfigureDAO.add(pcd);
                }
            }
        	
        	// 修改团购活动信息
        	groupbuyingInfoCmplReqDTO.setModifyTime(currentTime);
        	int groupbuyingInfoRet = groupbuyingInfoDAO.updateGroupbuyingInfo(groupbuyingInfoCmplReqDTO);
        	if(1 != groupbuyingInfoRet){
        		throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_NOT_EXIST.getCode(), "修改团购促销活动失败！");
        	}
        	

        	
        	// 添加团购价格设置信息  (先伪删除所有团购价格设置信息，然后再添加)
        	List<GroupbuyingPriceSettingReqDTO> groupbuyingPriceSettingReqDTOList = groupbuyingInfoCmplReqDTO.getGroupbuyingPriceSettingReqDTOList();
        	if(null != groupbuyingPriceSettingReqDTOList && groupbuyingPriceSettingReqDTOList.size() > 0 ){
        		
              	GroupbuyingPriceSettingReqDTO groupbuyingPriceSettingReqDTO_delete = new GroupbuyingPriceSettingReqDTO();
            	groupbuyingPriceSettingReqDTO_delete.setPromotionId(promotionId);
                groupbuyingPriceSettingReqDTO_delete.setDeleteFlag(Boolean.TRUE);
                groupbuyingPriceSettingReqDTO_delete.setModifyId(groupbuyingInfoCmplReqDTO.getModifyId());
                groupbuyingPriceSettingReqDTO_delete.setModifyName(groupbuyingInfoCmplReqDTO.getModifyName());
                groupbuyingPriceSettingReqDTO_delete.setModifyTime(currentTime);
            	groupbuyingPriceSettingDAO.pseudoDelete(groupbuyingPriceSettingReqDTO_delete);
            	
                	for(GroupbuyingPriceSettingReqDTO groupbuyingPriceSettingReqDTO : groupbuyingPriceSettingReqDTOList){
                    	groupbuyingPriceSettingReqDTO.setPromotionId(promotionId);
                		groupbuyingPriceSettingReqDTO.setItemId(groupbuyingInfoCmplReqDTO.getItemId());
                		groupbuyingPriceSettingReqDTO.setSkuCode(groupbuyingInfoCmplReqDTO.getSkuCode());
                		groupbuyingPriceSettingReqDTO.setDeleteFlag(Boolean.FALSE);
                		groupbuyingPriceSettingReqDTO.setCreateId(groupbuyingInfoCmplReqDTO.getModifyId());
                		groupbuyingPriceSettingReqDTO.setCreateName(groupbuyingInfoCmplReqDTO.getModifyName());
                		groupbuyingPriceSettingReqDTO.setCreateTime(currentTime);
                		groupbuyingPriceSettingReqDTO.setModifyId(groupbuyingInfoCmplReqDTO.getModifyId());
                		groupbuyingPriceSettingReqDTO.setModifyName(groupbuyingInfoCmplReqDTO.getModifyName());
                    	groupbuyingPriceSettingReqDTO.setModifyTime(currentTime);
                    	groupbuyingPriceSettingDAO.insert(groupbuyingPriceSettingReqDTO);
                	}
        	}
  

        	

            // 添加团购活动履历
            addPromotionStatusHistory(singlePromotionInfoReqDTO);
            
            // 异步初始化团购活动的Redis数据
            initGroupbuyingInfoRedisInfoWithThread(promotionId);

        } catch (Exception e) {
            logger.error("messageId{}:执行方法【updateGroupbuyingInfo】报错：{}", messageId, e.toString());
            throw new RuntimeException(e);
        }

    }
    

	@Override
	public String updateShowStatusByPromotionId(SinglePromotionInfoReqDTO singlePromotionInfoReqDTO,String messageId) {
		
		// 上下架操作返回状态码 [1001.参数为空,1002.活动编码为空,1003.上下架为空,1004.上下架状态不正确,1005.活动不存在,1006.活动已经上架,1007.活动已经下架]
    	
    	try {
    		
  		if (null == singlePromotionInfoReqDTO) {
  			return GroupbuyingConstants.CommonStatusEnum.UPDOWN_SHELVES_STATUS_1.key();//1001.参数为空
		}
		
  		String promotionId = singlePromotionInfoReqDTO.getPromotionId();
    	String showStatus = singlePromotionInfoReqDTO.getShowStatus();
    	
		if (null == promotionId || promotionId.length() == 0) {
			return GroupbuyingConstants.CommonStatusEnum.UPDOWN_SHELVES_STATUS_2.key();//1002.活动编码为空
		}
		
		if (null == showStatus || showStatus.length() == 0) {
			return GroupbuyingConstants.CommonStatusEnum.UPDOWN_SHELVES_STATUS_3.key();//1003.上下架为空
		}
		
		boolean isShowStatus= showStatus.equals(TimelimitedConstants.PromotionShowStatusEnum.VALID.key()) || showStatus.equals(TimelimitedConstants.PromotionShowStatusEnum.INVALID.key());
		if(!isShowStatus){
			return GroupbuyingConstants.CommonStatusEnum.UPDOWN_SHELVES_STATUS_4.key(); //1004.上下架状态不正确
		}
    	
		SinglePromotionInfoResDTO singlePromotionInfoResDTO = singlePromotionInfoDAO.selectByPromotionId(promotionId);
    	if(null == singlePromotionInfoResDTO){
    		return GroupbuyingConstants.CommonStatusEnum.UPDOWN_SHELVES_STATUS_5.key(); //1005.活动不存在
    	}
    	
    	if(showStatus.equals(TimelimitedConstants.PromotionShowStatusEnum.VALID.key())){//上架
    		if(singlePromotionInfoResDTO.getShowStatus().equals(showStatus)){//活动已经处于上架状态
    			return GroupbuyingConstants.CommonStatusEnum.UPDOWN_SHELVES_STATUS_6.key(); // 1006.活动已经上架
    		}
    		
    	}else{//下架
    		if(singlePromotionInfoResDTO.getShowStatus().equals(showStatus)){//活动已经处于下架状态
    			return GroupbuyingConstants.CommonStatusEnum.UPDOWN_SHELVES_STATUS_7.key(); //1007.活动已经下架
    		}
    	}
    	
		
        // 当前时间
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
    	
    	// 活动上下架
    	singlePromotionInfoReqDTO.setModifyTime(currentTime);
    	int singlePromotionInfoRet = singlePromotionInfoDAO.upDownShelvesPromotionInfo(singlePromotionInfoReqDTO);
    	if(1 != singlePromotionInfoRet){
    		return GroupbuyingConstants.CommonStatusEnum.STATUS_ERROR.key(); //-1.活动上下架失败
    	}
		
		// 更新redis里的上下架状态
		promotionGroupbuyingRedisHandle.upDownShelvesPromotionInfo2Redis(promotionId, showStatus);
		
         } catch (Exception e) {
             logger.error("messageId{}:执行方法【updateShowStatusByPromotionId】报错：{}", messageId, e.toString());
             throw new RuntimeException(e);
         }
    	
    	return GroupbuyingConstants.CommonStatusEnum.STATUS_SUCCESS.key(); //0.成功
	}
	
	@Override
	public String deleteGroupbuyingInfoByPromotionId(GroupbuyingInfoReqDTO groupbuyingInfoReqDTO, String messageId) {

		// 删除操作返回状态码 [1021.参数为空,1022.活动编码为空]
		try {

			if (null == groupbuyingInfoReqDTO) {
				return GroupbuyingConstants.CommonStatusEnum.DELGROUPBUYING_PARAM_IS_NULL.key();//1021.参数为空
			}

			if (null == groupbuyingInfoReqDTO.getPromotionId() || groupbuyingInfoReqDTO.getPromotionId().length() == 0) {
				return GroupbuyingConstants.CommonStatusEnum.DELGROUPBUYING_PROMOTIONID_IS_NULL.key();//1022.活动编码为空
			}

			// 当前时间
			Calendar calendar = Calendar.getInstance();
			Date currentTime = calendar.getTime();
			groupbuyingInfoReqDTO.setModifyTime(currentTime);
			int statusRet = groupbuyingInfoDAO.deleteByPromotionId(groupbuyingInfoReqDTO);
			if (1 != statusRet) {
				return GroupbuyingConstants.CommonStatusEnum.STATUS_ERROR.key(); //-1.活动删除失败
			}
			
			// 根据promotionId移除redis里的团购活动信息
			promotionGroupbuyingRedisHandle.removeGroupbuyingInfoCmpl2Redis(groupbuyingInfoReqDTO.getPromotionId());

		} catch (Exception e) {
			logger.error("messageId{}:执行方法【deleteGroupbuyingInfoByPromotionId】报错：{}",messageId, e.toString());
			throw new RuntimeException(e);
		}
		
		return GroupbuyingConstants.CommonStatusEnum.STATUS_SUCCESS.key(); //0.成功

	}
    

    /**
     * 根据促销活动的有效期间设定促销活动状态
     *
     * @param promotionInfo
     * @return
     */
    public String setPromotionStatusInfo(SinglePromotionInfoReqDTO promotionInfo) {
        Date nowDt = new Date();
        String status = promotionInfo.getStatus();
        String showStatus = promotionInfo.getShowStatus();
        if (dictionary
                .getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS, DictionaryConst.OPT_PROMOTION_STATUS_DELETE)
                .equals(status)) {
            return status;
        }
        if (dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID).equals(showStatus) || dictionary
                .getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                        DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_PASS).equals(showStatus)) {
            if (nowDt.before(promotionInfo.getEffectiveTime())) {
                status = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
                        DictionaryConst.OPT_PROMOTION_STATUS_NO_START);
            } else if (!nowDt.before(promotionInfo.getEffectiveTime()) && !nowDt
                    .after(promotionInfo.getInvalidTime())) {
                status = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
                        DictionaryConst.OPT_PROMOTION_STATUS_START);
            } else {
                status = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
                        DictionaryConst.OPT_PROMOTION_STATUS_END);
            }
        } else if (StringUtils.isEmpty(status)) {
            status = dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
                    DictionaryConst.OPT_PROMOTION_STATUS_NO_START);
        }
        promotionInfo.setStatus(status);
        return status;
    }



    /**
     * 添加团购活动履历
     * @param singlePromotionInfoReqDTO
     */
    private void addPromotionStatusHistory(SinglePromotionInfoReqDTO singlePromotionInfoReqDTO) {

        // 状态履历   状态 1：活动未开始，2：活动进行中，3：活动已结束，9：已删除 (暂时废弃)
//        PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();
//        historyDTO.setPromotionId(singlePromotionInfoReqDTO.getPromotionId());
//        historyDTO.setPromotionStatus(
//                dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS, singlePromotionInfoReqDTO.getStatus()));
//        historyDTO.setPromotionStatusText(
//                dictionary.getNameByValue(DictionaryConst.TYPE_PROMOTION_STATUS, singlePromotionInfoReqDTO.getStatus()));
//        historyDTO.setCreateId(singlePromotionInfoReqDTO.getModifyId());
//        historyDTO.setCreateName(singlePromotionInfoReqDTO.getModifyName());
//        promotionStatusHistoryDAO.add(historyDTO);

        // 促销活动展示状态履历   状态   1：待审核，2：审核通过，3：审核被驳回，4：启用，5：不启用
        PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();
        historyDTO.setPromotionId(singlePromotionInfoReqDTO.getPromotionId());
        historyDTO.setPromotionStatus(dictionary
                .getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS, singlePromotionInfoReqDTO.getShowStatus()));
        historyDTO.setPromotionStatusText(dictionary
                .getNameByValue(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS, singlePromotionInfoReqDTO.getShowStatus()));
        historyDTO.setCreateId(singlePromotionInfoReqDTO.getModifyId());
        historyDTO.setCreateName(singlePromotionInfoReqDTO.getModifyName());
        promotionStatusHistoryDAO.add(historyDTO);
    }

	@Override
	public void initGroupbuyingInfoRedisInfoWithThread(final String promotionId) {
        new Thread() {
            public void run() {
                String messageId = keyGeneratorUtils.generateMessageId();
            	GroupbuyingInfoCmplResDTO groupbuyingInfoCmplResDTO = getGroupbuyingInfoCmplByPromotionId(promotionId, messageId);
            	initGroupbuyingInfoRedisInfo(groupbuyingInfoCmplResDTO);
            }
        }.start();
	}

	@Override
	public void initGroupbuyingInfoRedisInfo(GroupbuyingInfoCmplResDTO groupbuyingInfoCmplResDTO) {

        PromotionInfoDMO promotionInfoDMO = new PromotionInfoDMO();
        promotionInfoDMO.setPromotionId(groupbuyingInfoCmplResDTO.getPromotionId());
        try {
        	// 保存团购活动信息到redis
        	promotionGroupbuyingRedisHandle.setGroupbuyingInfoCmpl2Redis(groupbuyingInfoCmplResDTO);
            promotionInfoDMO.setDealFlag(YesNoEnum.NO.getValue());
        } catch (Exception e) {
            promotionInfoDMO.setDealFlag(YesNoEnum.YES.getValue());
            logger.info("初始化秒杀活动Redis数据GroupbuyingServiceImpl-initGroupbuyingInfoRedisInfo:入参:{},异常:{}",
                    JSON.toJSONString(groupbuyingInfoCmplResDTO), ExceptionUtils.getFullStackTrace(e));
            throw new RuntimeException(e);
        } finally {
            promotionInfoDAO.updatePromotionDealFlag(promotionInfoDMO);
        }
	}

	@Override
	public GroupbuyingInfoCmplResDTO getGroupbuyingInfoCmplByPromotionId(String promotionId, String messageId) {
		
		GroupbuyingInfoCmplResDTO groupbuyingInfoCmplResDTO = null;

	        try {

	            if (null == promotionId || promotionId.length() == 0) {
	                throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "团购促销活动编号不能为空！");
	            }

	            // 查询活动信息
	            groupbuyingInfoCmplResDTO = groupbuyingInfoDAO.getGroupbuyingInfoCmplByPromotionId(promotionId);
	            
	            List<PromotionConfigureDTO> promotionConfigureDTOlist = promotionConfigureDAO.getPromotionConfiguresByPromotionId(promotionId);
	            // 设置配置信息
	            if(null != groupbuyingInfoCmplResDTO && null != groupbuyingInfoCmplResDTO.getSinglePromotionInfoCmplResDTO()){
	            	groupbuyingInfoCmplResDTO.getSinglePromotionInfoCmplResDTO().setPromotionConfigureList(promotionConfigureDTOlist);
	            }

	        } catch (Exception e) {
	            logger.error("messageId{}:执行方法【getGroupbuyingInfoCmplByPromotionId】报错：{}", messageId, e.toString());
	            throw new RuntimeException(e);
	        }

	        return groupbuyingInfoCmplResDTO;
	}
	
	
	@Override
	public GroupbuyingInfoResDTO getSingleGroupbuyingInfoByPromotionId(String promotionId, String messageId) {
		
		GroupbuyingInfoResDTO groupbuyingInfoResDTO = null;

	        try {

	            if (null == promotionId || promotionId.length() == 0) {
	                throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "团购促销活动编号不能为空！");
	            }
	            
	            // 查询活动信息
	            groupbuyingInfoResDTO = groupbuyingInfoDAO.selectByPromotionId(promotionId);

	        } catch (Exception e) {
	            logger.error("messageId{}:执行方法【getSingleGroupbuyingInfoByPromotionId】报错：{}", messageId, e.toString());
	            throw new RuntimeException(e);
	        }

	        return groupbuyingInfoResDTO;
	}

	@Override
	public DataGrid<GroupbuyingInfoCmplResDTO> getGroupbuyingInfoCmplForPage(Pager<GroupbuyingInfoReqDTO> page,GroupbuyingInfoReqDTO groupbuyingInfoReqDTO, String messageId) {
		

        DataGrid<GroupbuyingInfoCmplResDTO> dataGrid = null;
        try {
//            if (null == groupbuyingInfoReqDTO) {
//                throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "团购促销活动参数不能为空！");
//            }

            dataGrid = new DataGrid<GroupbuyingInfoCmplResDTO>();
            List<GroupbuyingInfoCmplResDTO> groupbuyingInfoCmplResDTOList = groupbuyingInfoDAO.getGroupbuyingInfoCmplForPage(page, groupbuyingInfoReqDTO);
            int count = groupbuyingInfoDAO.getGroupbuyingInfoCmplCount(groupbuyingInfoReqDTO);
            dataGrid.setTotal(Long.valueOf(String.valueOf(count)));
            dataGrid.setRows(groupbuyingInfoCmplResDTOList);
        } catch (Exception e) {
            logger.error("messageId{}:执行方法【getGroupbuyingInfoCmplForPage】报错：{}", messageId, e.toString());
            throw new RuntimeException(e);
        }
        return dataGrid;
		
	}
	

    @Override
    public void addGroupbuyingRecord2HttpINTFC(GroupbuyingRecordReqDTO groupbuyingRecordReqDTO, String messageId) {
       
        // 当前时间
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();

        try {
        	//[start]-----------------   参团的校验  ----------------
           	String groupbuyingInfoJsonStr = promotionGroupbuyingRedisHandle.getPromotionCenterRedisDB().getHash(RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO, groupbuyingRecordReqDTO.getPromotionId());
           	if(null == groupbuyingInfoJsonStr || groupbuyingInfoJsonStr.length() == 0) {
           	    throw new PromotionCenterBusinessException(ResultCodeEnum.NORESULT.getCode(), "团购促销活动不存在！");
           	}
           	GroupbuyingInfoCmplResDTO groupbuyingInfoCmplResDTO = JSON.parseObject(groupbuyingInfoJsonStr, GroupbuyingInfoCmplResDTO.class);
            if (null == groupbuyingInfoCmplResDTO) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.NORESULT.getCode(), "团购促销活动不存在！");
            }
       		// 开团开始时间
       		Date effectiveTime = groupbuyingInfoCmplResDTO.getSinglePromotionInfoCmplResDTO().getEffectiveTime();
       		// 开团结束时间
       		Date invalidTime = groupbuyingInfoCmplResDTO.getSinglePromotionInfoCmplResDTO().getInvalidTime();
       	    //(当前时间 >= 开团开始时间 && 当前时间 <= 开团结束时间)  2.开团进行中
            boolean isGBProcessing = currentTime.getTime() >= effectiveTime.getTime() && currentTime.getTime() <= invalidTime.getTime();
            if(!isGBProcessing){
            	 throw new PromotionCenterBusinessException(ResultCodeEnum.ERROR.getCode(), "团购促销活动状态不是开团进行中！");
            }
            
            GroupbuyingRecordReqDTO groupbuyingRecordReqDTO_check = new GroupbuyingRecordReqDTO();
            groupbuyingRecordReqDTO_check.setPromotionId(groupbuyingRecordReqDTO.getPromotionId());// 促销活动编码
            groupbuyingRecordReqDTO_check.setBuyerCode(groupbuyingRecordReqDTO.getBuyerCode());// 参团人账号
            GroupbuyingRecordResDTO groupbuyingRecordResDTO = groupbuyingRecordDAO.getGroupbuyingRecordByParams(groupbuyingRecordReqDTO_check);
            if(null != groupbuyingRecordResDTO){
            	 throw new PromotionCenterBusinessException(ResultCodeEnum.ERROR.getCode(), "已经参团过！");
            }
          //[end]-----------------   参团的校验  ----------------
            
    		
        	 String groupbuyingResultKey = RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO_RESULT + "_" + groupbuyingRecordReqDTO.getPromotionId();
         	// 真实参团人数
         	Integer realActorCount = promotionGroupbuyingRedisHandle.getPromotionCenterRedisDB().incrHash(groupbuyingResultKey, RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO_REAL_ACTOR_COUNT).intValue();
        	// 获取团购活动其他信息
        	Map<String, String> resultMap = promotionGroupbuyingRedisHandle.getPromotionCenterRedisDB().getHashOperations(groupbuyingResultKey);
        	
        	// 阶梯价格
        	String groupbuyingPriceSettingStr = String.valueOf(resultMap.get(RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO_PRICESETTING));
        	List<GroupbuyingPriceSettingResDTO> groupbuyingPriceSettingResDTOList = JSONObject.parseArray(groupbuyingPriceSettingStr,GroupbuyingPriceSettingResDTO.class);
        	// 团购价格设置降序排序(sortNum)
        	Collections.sort(groupbuyingPriceSettingResDTOList, new Comparator<GroupbuyingPriceSettingResDTO>(){
				@Override
				public int compare(GroupbuyingPriceSettingResDTO o1,GroupbuyingPriceSettingResDTO o2) {
					//默认为升序
					//o1.getSortNum().compareTo(o2.getSortNum())
					return o2.getSortNum().compareTo(o1.getSortNum());
				}
        	});
        	
        	// 真实拼团价
        	BigDecimal realGroupbuyingPrice = null;
        	for(int i = 0;i<groupbuyingPriceSettingResDTOList.size();i++){
        		GroupbuyingPriceSettingResDTO groupbuyingPriceSettingResDTO = groupbuyingPriceSettingResDTOList.get(i);
        		Integer actorCount = groupbuyingPriceSettingResDTO.getActorCount();// 参团人数
        		if(realActorCount >= actorCount){
        			realGroupbuyingPrice = groupbuyingPriceSettingResDTO.getGroupbuyingPrice();// 拼团价
        			break;
        		}
        	}
        	// redis设置真实拼团价
        	promotionGroupbuyingRedisHandle.getPromotionCenterRedisDB().setHash(groupbuyingResultKey, RedisConst.PROMOTION_REDIS_GROUPBUYINGINFO_REAL_GROUPBUYINGPRICE, realGroupbuyingPrice.toString());

        	// 修改团购活动信息
        	GroupbuyingInfoReqDTO groupbuyingInfoReqDTO = new GroupbuyingInfoReqDTO();
        	groupbuyingInfoReqDTO.setPromotionId(groupbuyingRecordReqDTO.getPromotionId());
        	groupbuyingInfoReqDTO.setRealActorCount(realActorCount);// 真实参团人数
        	groupbuyingInfoReqDTO.setRealGroupbuyingPrice(realGroupbuyingPrice);// 真实拼团价
        	groupbuyingInfoReqDTO.setModifyId(groupbuyingRecordReqDTO.getModifyId());
        	groupbuyingInfoReqDTO.setModifyName(groupbuyingRecordReqDTO.getModifyName());
        	groupbuyingInfoReqDTO.setModifyTime(currentTime);
        	groupbuyingInfoDAO.updateGroupbuyingInfo(groupbuyingInfoReqDTO);
        	
        	// 添加参团记录
        	groupbuyingRecordReqDTO.setDeleteFlag(Boolean.FALSE);
        	groupbuyingRecordReqDTO.setCreateTime(currentTime);
        	groupbuyingRecordReqDTO.setModifyTime(currentTime);
        	groupbuyingRecordDAO.addGroupbuyingRecord(groupbuyingRecordReqDTO);
        	
        } catch (Exception e) {
            logger.error("messageId{}:执行方法【addGroupbuyingRecord2HttpINTFC】报错：{}", messageId, e.toString());
            throw new RuntimeException(e);
        }
    }

	@Override
	public GroupbuyingRecordResDTO getSingleGroupbuyingRecord(GroupbuyingRecordReqDTO groupbuyingRecordReqDTO, String messageId) {
		
		GroupbuyingRecordResDTO groupbuyingRecordResDTO = null;

	        try {

	            if (null == groupbuyingRecordReqDTO) {
	                throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "团购促销活动参数不能为空！");
	            }

	            groupbuyingRecordResDTO = groupbuyingRecordDAO.getGroupbuyingRecordByParams(groupbuyingRecordReqDTO);

	        } catch (Exception e) {
	            logger.error("messageId{}:执行方法【getSingleGroupbuyingRecord】报错：{}", messageId, e.toString());
	            throw new RuntimeException(e);
	        }

	        return groupbuyingRecordResDTO;
	}

	@Override
	public DataGrid<GroupbuyingRecordResDTO> geGroupbuyingRecordForPage(Pager<GroupbuyingRecordReqDTO> page, GroupbuyingRecordReqDTO groupbuyingRecordReqDTO, String messageId) {

        DataGrid<GroupbuyingRecordResDTO> dataGrid = null;
        try {

            dataGrid = new DataGrid<GroupbuyingRecordResDTO>();
            List<GroupbuyingRecordResDTO> groupbuyingRecordResDTOList = groupbuyingRecordDAO.getGroupbuyingRecordForPage(page, groupbuyingRecordReqDTO);
            int count = groupbuyingRecordDAO.getGroupbuyingRecordCount(groupbuyingRecordReqDTO);
            dataGrid.setTotal(Long.valueOf(String.valueOf(count)));
            dataGrid.setRows(groupbuyingRecordResDTOList);
        } catch (Exception e) {
            logger.error("messageId{}:执行方法【geGroupbuyingRecordForPage】报错：{}", messageId, e.toString());
            throw new RuntimeException(e);
        }
        return dataGrid;
	}

	@Override
	public DataGrid<GroupbuyingInfoCmplResDTO> getGroupbuyingInfo4MobileForPage(Pager<GroupbuyingInfoReqDTO> page, GroupbuyingInfoReqDTO groupbuyingInfoReqDTO, String messageId) {
//		if (StringUtils.isEmpty(groupbuyingInfoReqDTO.getSellerCode())) {
//			throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "团购促销活动参数orgId不能为空！");
//		}
//		if (StringUtils.isEmpty(groupbuyingInfoReqDTO.getBuyerCode())) {
//			throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "团购促销活动参数buyerCode不能为空！");
//		}

    	DataGrid<GroupbuyingInfoCmplResDTO> dataGrid = null;
		try {
			dataGrid = new DataGrid<GroupbuyingInfoCmplResDTO>();
			List<GroupbuyingInfoCmplResDTO> groupbuyingInfoCmplResDTOList = groupbuyingInfoDAO.getGroupbuyingInfo4MobileForPage(page, groupbuyingInfoReqDTO);
			int count = groupbuyingInfoDAO.getGroupbuyingInfo4MobileCount(groupbuyingInfoReqDTO);
			dataGrid.setTotal(Long.valueOf(String.valueOf(count)));
			dataGrid.setRows(groupbuyingInfoCmplResDTOList);
		} catch (Exception e) {
			logger.error("messageId{}:执行方法【getGroupbuyingInfo4MobileForPage】报错：{}", messageId, e.toString());
			throw new RuntimeException(e);
		}
		return dataGrid;
	}
}
