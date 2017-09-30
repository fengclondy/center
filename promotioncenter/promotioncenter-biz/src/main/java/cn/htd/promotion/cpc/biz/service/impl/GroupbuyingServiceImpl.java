package cn.htd.promotion.cpc.biz.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.biz.dao.GroupbuyingInfoDAO;
import cn.htd.promotion.cpc.biz.dao.GroupbuyingPriceSettingDAO;
import cn.htd.promotion.cpc.biz.dao.GroupbuyingRecordDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionConfigureDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionStatusHistoryDAO;
import cn.htd.promotion.cpc.biz.dao.SinglePromotionInfoDAO;
import cn.htd.promotion.cpc.biz.handle.PromotionTimelimitedRedisHandle;
import cn.htd.promotion.cpc.biz.service.GroupbuyingService;
import cn.htd.promotion.cpc.common.constants.TimelimitedConstants;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.emums.YesNoEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.GeneratorUtils;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.request.GroupbuyingInfoCmplReqDTO;
import cn.htd.promotion.cpc.dto.request.GroupbuyingPriceSettingReqDTO;
import cn.htd.promotion.cpc.dto.request.SinglePromotionInfoCmplReqDTO;
import cn.htd.promotion.cpc.dto.request.SinglePromotionInfoReqDTO;
import cn.htd.promotion.cpc.dto.request.TimelimitedSkuDescribeReqDTO;
import cn.htd.promotion.cpc.dto.request.TimelimitedSkuPictureReqDTO;
import cn.htd.promotion.cpc.dto.response.GroupbuyingInfoResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionAccumulatyDTO;
import cn.htd.promotion.cpc.dto.response.PromotionConfigureDTO;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionStatusHistoryDTO;

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
    private PromotionRedisDB promotionRedisDB;

    @Resource
    private DictionaryUtils dictionary;
    
    @Resource
    private GeneratorUtils noGenerator;

    @Resource
    private PromotionStatusHistoryDAO promotionStatusHistoryDAO;
    
    @Resource
	private PromotionTimelimitedRedisHandle promotionTimelimitedRedisHandle;
    

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


            // 异步初始化秒杀活动的Redis数据
//            TimelimitedInfoResDTO timelimitedInfoResDTO =
//                    getSingleFullTimelimitedInfoByPromotionId(promotionExtendInfoReturn.getPromotionId(),TimelimitedConstants.TYPE_DATA_TIMELIMITED_REAL_REMAIN_COUNT, messageId);
//            initTimelimitedInfoRedisInfoWithThread(timelimitedInfoResDTO);

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
        	promotionConfigureDAO.deleteByPromotionId(promotionId);
        	SinglePromotionInfoCmplReqDTO singlePromotionInfoCmplReqDTO = (SinglePromotionInfoCmplReqDTO) singlePromotionInfoReqDTO;
            List<PromotionConfigureDTO> pclist = singlePromotionInfoCmplReqDTO.getPromotionConfigureList();
            if (pclist != null && pclist.size() > 0) {
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
        	GroupbuyingPriceSettingReqDTO groupbuyingPriceSettingReqDTO_delete = new GroupbuyingPriceSettingReqDTO();
        	groupbuyingPriceSettingReqDTO_delete.setPromotionId(promotionId);
            groupbuyingPriceSettingReqDTO_delete.setDeleteFlag(Boolean.TRUE);
            groupbuyingPriceSettingReqDTO_delete.setModifyId(groupbuyingInfoCmplReqDTO.getModifyId());
            groupbuyingPriceSettingReqDTO_delete.setModifyName(groupbuyingInfoCmplReqDTO.getModifyName());
            groupbuyingPriceSettingReqDTO_delete.setModifyTime(currentTime);
        	groupbuyingPriceSettingDAO.pseudoDelete(groupbuyingPriceSettingReqDTO_delete);
        	List<GroupbuyingPriceSettingReqDTO> groupbuyingPriceSettingReqDTOList = groupbuyingInfoCmplReqDTO.getGroupbuyingPriceSettingReqDTOList();
        	if(null != groupbuyingPriceSettingReqDTOList && groupbuyingPriceSettingReqDTOList.size() > 0){
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


        } catch (Exception e) {
            logger.error("messageId{}:执行方法【updateGroupbuyingInfo】报错：{}", messageId, e.toString());
            throw new RuntimeException(e);
        }

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
        historyDTO.setCreateId(singlePromotionInfoReqDTO.getCreateId());
        historyDTO.setCreateName(singlePromotionInfoReqDTO.getCreateName());
        promotionStatusHistoryDAO.add(historyDTO);
    }



}
