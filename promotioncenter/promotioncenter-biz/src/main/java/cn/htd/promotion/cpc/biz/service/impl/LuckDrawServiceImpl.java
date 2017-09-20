package cn.htd.promotion.cpc.biz.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.common.util.SysProperties;
import cn.htd.promotion.cpc.biz.dao.BuyerWinningRecordDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionAwardInfoDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionDetailDescribeDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionInfoDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionStatusHistoryDAO;
import cn.htd.promotion.cpc.biz.dmo.BuyerWinningRecordDMO;
import cn.htd.promotion.cpc.biz.dmo.PromotionDetailDescribeDMO;
import cn.htd.promotion.cpc.biz.dmo.WinningRecordResDMO;
import cn.htd.promotion.cpc.biz.service.LuckDrawService;
import cn.htd.promotion.cpc.biz.service.PromotionBaseService;
import cn.htd.promotion.cpc.biz.service.PromotionLotteryCommonService;
import cn.htd.promotion.cpc.biz.service.PromotionLotteryService;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.emums.YesNoEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.ExceptionUtils;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.request.DrawLotteryReqDTO;
import cn.htd.promotion.cpc.dto.request.LotteryActivityPageReqDTO;
import cn.htd.promotion.cpc.dto.request.LotteryActivityRulePageReqDTO;
import cn.htd.promotion.cpc.dto.request.ScratchCardActivityPageReqDTO;
import cn.htd.promotion.cpc.dto.request.ShareLinkHandleReqDTO;
import cn.htd.promotion.cpc.dto.request.ValidateLuckDrawReqDTO;
import cn.htd.promotion.cpc.dto.request.ValidateScratchCardReqDTO;
import cn.htd.promotion.cpc.dto.request.WinningRecordReqDTO;
import cn.htd.promotion.cpc.dto.response.BuyerWinningRecordDTO;
import cn.htd.promotion.cpc.dto.response.LotteryActivityPageResDTO;
import cn.htd.promotion.cpc.dto.response.LotteryActivityRulePageResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionAccumulatyDTO;
import cn.htd.promotion.cpc.dto.response.PromotionAwardInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionPictureDTO;
import cn.htd.promotion.cpc.dto.response.PromotionSellerDetailDTO;
import cn.htd.promotion.cpc.dto.response.PromotionSellerRuleDTO;
import cn.htd.promotion.cpc.dto.response.PromotionStatusHistoryDTO;
import cn.htd.promotion.cpc.dto.response.ShareLinkHandleResDTO;
import cn.htd.promotion.cpc.dto.response.ValidateLuckDrawResDTO;
import cn.htd.promotion.cpc.dto.response.ValidateScratchCardResDTO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service("luckDrawService")
public class LuckDrawServiceImpl implements LuckDrawService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LuckDrawServiceImpl.class);
	// 抽奖活动最大奖品数量
	private static final String PROMOTION_LOTTERY_MAX_AWARD_SIZE = "promotion.lottery.max.award.size";
	@Resource
	private BuyerWinningRecordDAO awardRecordDAO;

	@Resource
	private PromotionDetailDescribeDAO promotionDetailDescribeDAO;

	@Resource
	private PromotionRedisDB promotionRedisDB;

	@Resource
	private DictionaryUtils dictionary;

	@Resource
	private PromotionBaseService promotionBaseService;

	@Resource
	private PromotionStatusHistoryDAO promotionStatusHistoryDAO;

	@Resource
	private PromotionAwardInfoDAO promotionAwardInfoDAO;

	@Resource
	private PromotionInfoDAO promotionInfoDAO;

	@Resource
	private PromotionBaseService baseService;

	@Resource
	public BuyerWinningRecordDAO buyerWinningRecordDAO;

	@Resource
	private PromotionLotteryCommonService promotionLotteryCommonService;
	
	@Resource
	private PromotionLotteryService promotionLotteryService;

	@Override
	public ValidateLuckDrawResDTO validateLuckDrawPermission(
			ValidateLuckDrawReqDTO requestDTO) {
		String messageId = requestDTO.getMessageId();
		ValidateLuckDrawResDTO result = new ValidateLuckDrawResDTO();
		try {
			result.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
			result.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
			String gashaphonType = dictionary.getValueByCode(
					DictionaryConst.TYPE_PROMOTION_TYPE,
					DictionaryConst.OPT_PROMOTION_TYPE_GASHAPON);
			String promotionId = queryEffectivePromotion(gashaphonType);
			if (StringUtils.isEmpty(promotionId)) {
				result.setResponseCode(ResultCodeEnum.PROMOTION_NOT_EXIST
						.getCode());
				result.setResponseMsg(ResultCodeEnum.PROMOTION_NOT_EXIST
						.getMsg());
				return result;
			}
			result.setPromotionId(promotionId);
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getCode());
			result.setResponseMsg(ResultCodeEnum.ERROR.getMsg());
			LOGGER.error(
					"MessageId:{} 调用方法LuckDrawServiceImpl.validateLuckDrawPermission出现异常 异常信息：{}",
					messageId, ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public LotteryActivityPageResDTO lotteryActivityPage(
			LotteryActivityPageReqDTO request) {
		String messageId = request.getMessageId();
		String promotionId = request.getPromotionId();
		PromotionExtendInfoDTO promotionInfoDTO = null;
		LotteryActivityPageResDTO result = new LotteryActivityPageResDTO();
		List<PromotionPictureDTO> promotionPictureList = null;
		List<String> pictureUrlList = new ArrayList<String>();
		Map<String, String> dictMap = null;
		DrawLotteryReqDTO checkInfoDTO = new DrawLotteryReqDTO();
		try {
			result.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
			result.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
			dictMap = baseService.initPromotionDictMap();
			promotionInfoDTO = promotionLotteryCommonService
					.getRedisLotteryInfo(promotionId, dictMap);
			promotionPictureList = promotionInfoDTO.getPromotionPictureList();
			if (CollectionUtils.isNotEmpty(promotionPictureList)) {
				for (PromotionPictureDTO promotionPicture : promotionPictureList) {
					pictureUrlList.add(promotionPicture
							.getPromotionPictureUrl());
				}
			}
			result.setActivityStartTime(promotionInfoDTO.getEachStartTime());
			result.setActivityEndTime(promotionInfoDTO.getEachEndTime());
			result.setEffectiveTime(promotionInfoDTO.getEffectiveTime());
			result.setInvalidTime(promotionInfoDTO.getInvalidTime());
			result.setPromotionName(promotionInfoDTO.getPromotionName());
			result.setPictureUrl(pictureUrlList);
			result.setRemainingTimes(0);
			checkInfoDTO.setPromotionId(promotionId);
			checkInfoDTO.setBuyerCode(request.getMemberNo());
			checkInfoDTO.setSellerCode(request.getOrgId());
			checkInfoDTO.setIsBuyerFirstLogin(request.getIsBuyerFirstLogin());
			promotionLotteryCommonService.checkPromotionLotteryValid(
					promotionInfoDTO, checkInfoDTO, dictMap);
			setMemberOrgIdRedis(promotionId, request.getMemberNo(),
					request.getOrgId(), promotionInfoDTO, result);
		} catch (PromotionCenterBusinessException pcbe) {
			result.setResponseCode(pcbe.getCode());
			result.setResponseMsg(pcbe.getMessage());
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getCode());
			result.setResponseMsg(ResultCodeEnum.ERROR.getMsg());
			LOGGER.error(
					"MessageId:{} 调用方法LuckDrawServiceImpl.lotteryActivityPage出现异常 request：{}异常信息：{}",
					messageId, JSONObject.toJSONString(request),
					ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	/**
	 * 设置粉丝会员店等相关redis值
	 */
	private void setMemberOrgIdRedis(String promotionId, String buyerNo,
			String orgId, PromotionExtendInfoDTO promotionInfoDTO,
			LotteryActivityPageResDTO result) {
		// 粉丝活动粉丝当日次数信息Key
		String b2bMiddleLotteryBuyerTimesInfoKey = RedisConst.REDIS_LOTTERY_BUYER_TIMES_INFO
				+ "_" + promotionId + "_" + buyerNo;
		String sellerWinedTimesKey = RedisConst.REDIS_LOTTERY_SELLER_WINED_TIMES
				+ "_" + promotionId + "_" + orgId;
		String remainTimes = "";
		Map<String, String> buyerTimeInfoMap = new HashMap<String, String>();
		if (!promotionRedisDB.exists(sellerWinedTimesKey)) {
			String dailyWinningTimes = "";
			if (null != promotionInfoDTO.getIsDailyWinningLimit()
					&& YesNoEnum.YES.getValue() == promotionInfoDTO
							.getIsDailyWinningLimit().intValue()) {
				dailyWinningTimes = promotionInfoDTO.getDailyWinningTimes()
						.toString();
			} else {
				dailyWinningTimes = Integer.MAX_VALUE + "";
			}
			promotionRedisDB.setAndExpire(sellerWinedTimesKey,
					dailyWinningTimes, promotionInfoDTO.getInvalidTime());
		}
		if (!StringUtils.isEmpty(buyerNo)) {
			if (!promotionRedisDB.exists(b2bMiddleLotteryBuyerTimesInfoKey)) {
				// 粉丝活动粉丝当日剩余参与次数
				String dailyBuyerPartakeTimes = "";
				if (null != promotionInfoDTO.getIsDailyTimesLimit()
						&& YesNoEnum.YES.getValue() == promotionInfoDTO
								.getIsDailyTimesLimit()) {
					dailyBuyerPartakeTimes = promotionInfoDTO
							.getDailyBuyerPartakeTimes().toString();
				} else {
					// 没有抽奖次数限制，设置为最大值
					dailyBuyerPartakeTimes = Integer.MAX_VALUE + "";
				}
				buyerTimeInfoMap.put(
						RedisConst.REDIS_LOTTERY_BUYER_PARTAKE_TIMES,
						dailyBuyerPartakeTimes);

				// 粉丝当日中奖次数
				String dailyBuyerWinningTimes = "";
				if (null != promotionInfoDTO.getIsDailyWinningLimit()
						&& YesNoEnum.YES.getValue() == promotionInfoDTO
								.getIsDailyWinningLimit()) {
					dailyBuyerWinningTimes = promotionInfoDTO
							.getDailyBuyerWinningTimes().toString();
				} else {
					// 没有中奖次数限制，设置为最大值
					dailyBuyerWinningTimes = Integer.MAX_VALUE + "";
				}

				buyerTimeInfoMap.put(
						RedisConst.REDIS_LOTTERY_BUYER_WINNING_TIMES,
						dailyBuyerWinningTimes);

				buyerTimeInfoMap.put(
						RedisConst.REIDS_LOTTERY_BUYER_SHARE_TIMES, "0");
				buyerTimeInfoMap.put(
						RedisConst.REDIS_LOTTERY_BUYER_HAS_TOP_EXTRA_TIMES,
						String.valueOf(YesNoEnum.NO.getValue()));
				promotionRedisDB.setHash(b2bMiddleLotteryBuyerTimesInfoKey,
						buyerTimeInfoMap);
				promotionRedisDB.setExpire(b2bMiddleLotteryBuyerTimesInfoKey,
						promotionInfoDTO.getInvalidTime());
			}
			remainTimes = promotionRedisDB.getHash(
					b2bMiddleLotteryBuyerTimesInfoKey,
					RedisConst.REDIS_LOTTERY_BUYER_PARTAKE_TIMES);
			result.setRemainingTimes(StringUtils.isEmpty(remainTimes) ? 0
					: Integer.valueOf(remainTimes));
			if (result.getRemainingTimes() < 0) {
				result.setRemainingTimes(0);
			}
		}
	}

	@Override
	public LotteryActivityRulePageResDTO lotteryActivityRulePage(
			LotteryActivityRulePageReqDTO request) {
		String messageId = request.getMessageId();
		LotteryActivityRulePageResDTO result = new LotteryActivityRulePageResDTO();
		try {
			PromotionDetailDescribeDMO record = new PromotionDetailDescribeDMO();
			record.setPromotionId(request.getPromotionId());
			PromotionDetailDescribeDMO promotionDetailDescribeInfo = promotionDetailDescribeDAO
					.selectByPromotionId(record);
			if (null != promotionDetailDescribeInfo
					&& StringUtils.isNotEmpty(promotionDetailDescribeInfo
							.getDescribeContent())) {
				result.setActivityRuleContent(promotionDetailDescribeInfo
						.getDescribeContent());
				result.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
				result.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
			} else {
				result.setResponseCode(ResultCodeEnum.LOTTERY_NO_DESCRIBE_CONTENT
						.getCode());
				result.setResponseMsg(ResultCodeEnum.LOTTERY_NO_DESCRIBE_CONTENT
						.getMsg());
			}
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getCode());
			result.setResponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法LuckDrawServiceImpl.lotteryActivityRulePage出现异常 request：{}异常信息：{}",
					messageId, JSONObject.toJSONString(request), w.toString());
		}
		return result;
	}

	@Override
	public ShareLinkHandleResDTO shareLinkHandle(ShareLinkHandleReqDTO request) {
		String messageId = request.getMessageId();
		ShareLinkHandleResDTO result = new ShareLinkHandleResDTO();
		try {
			String promotionId = request.getPromotionId();
			String lotteryTimesInfo = RedisConst.REDIS_LOTTERY_TIMES_INFO + "_"
					+ promotionId;
			// 每次分享获得抽奖次数
			String buyerShareExtraPartakeTimes = promotionRedisDB.getHash(
					lotteryTimesInfo,
					RedisConst.REDIS_LOTTERY_BUYER_SHARE_EXTRA_PARTAKE_TIMES);
			String buyerCode = request.getMemberNo();
			String lotteryBuyerTimes = RedisConst.REDIS_LOTTERY_BUYER_TIMES_INFO
					+ "_" + promotionId + "_" + buyerCode;
			if (StringUtils.isNotEmpty(buyerShareExtraPartakeTimes)) {
				// 粉丝分享获得抽奖次数上限
				String buyerTopExtraPartakeTime = promotionRedisDB.getHash(
						lotteryTimesInfo,
						RedisConst.REDIS_LOTTERY_BUYER_TOP_EXTRA_PARTAKE_TIMES);
				Long partakeTime = Long.valueOf(buyerShareExtraPartakeTimes);
				if (StringUtils.isEmpty(buyerTopExtraPartakeTime)
						|| Integer.valueOf(buyerTopExtraPartakeTime) < 0) {
					// 粉丝活动粉丝当日参与次数--总共剩余参与次数
					promotionRedisDB.incrHashBy(lotteryBuyerTimes,
							RedisConst.REDIS_LOTTERY_BUYER_PARTAKE_TIMES,
							partakeTime);
				} else {
					// 粉丝分享次数
					String memberShareTimes = promotionRedisDB.getHash(
							lotteryBuyerTimes,
							RedisConst.REIDS_LOTTERY_BUYER_SHARE_TIMES);
					// 粉丝分享获得的抽奖次数= 粉丝分享次数 * 粉丝分享一次获得的抽奖次数
					if (StringUtils.isNotEmpty(buyerShareExtraPartakeTimes)
							&& StringUtils.isNotEmpty(memberShareTimes)) {
						int memberShareGetTimes = Integer
								.valueOf(buyerShareExtraPartakeTimes)
								* Integer.valueOf(memberShareTimes);
						if (memberShareGetTimes < Integer
								.valueOf(buyerTopExtraPartakeTime)) {
							promotionRedisDB
									.incrHashBy(
											lotteryBuyerTimes,
											RedisConst.REDIS_LOTTERY_BUYER_PARTAKE_TIMES,
											Long.valueOf(buyerShareExtraPartakeTimes));
						} else {
							promotionRedisDB
									.setHash(
											lotteryBuyerTimes,
											RedisConst.REDIS_LOTTERY_BUYER_HAS_TOP_EXTRA_TIMES,
											String.valueOf(YesNoEnum.YES
													.getValue()));
						}
					}
				}
			}
			// 粉丝分享次数
			promotionRedisDB.incrHash(lotteryBuyerTimes,
					RedisConst.REIDS_LOTTERY_BUYER_SHARE_TIMES);
			// 更新粉丝抽奖次数成功
			result.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
			result.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getCode());
			result.setResponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法LuckDrawServiceImpl.shareLinkHandle出现异常 request：{}异常信息：{}",
					messageId, JSONObject.toJSONString(request), w.toString());
		}
		return result;
	}

	@Override
	public WinningRecordResDMO queryWinningRecord(WinningRecordReqDTO request) {
		String messageId = request.getMessageId();
		WinningRecordResDMO result = new WinningRecordResDMO();
		try {
			BuyerWinningRecordDMO buyerWinningRecordDMO = new BuyerWinningRecordDMO();
			buyerWinningRecordDMO.setBuyerCode(request.getMemberNo());
			// 页面传入的开始位置减一
			Integer startNo = request.getStartNo() == null ? new Integer(0)
					: request.getStartNo() - 1;
			Integer endNo = request.getEndNo() == null ? new Integer(10)
					: request.getEndNo();
			buyerWinningRecordDMO.setStartNo(startNo);
			buyerWinningRecordDMO.setEndNo(endNo);
			List<BuyerWinningRecordDMO> winningRecordList = awardRecordDAO
					.queryWinningRecord(buyerWinningRecordDMO);
			result.setWinningRecordList(winningRecordList);
			result.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
			result.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getCode());
			result.setResponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法LuckDrawServiceImpl.queryWinningRecord出现异常 request：{}异常信息：{}",
					messageId, JSONObject.toJSONString(request), w.toString());
		}
		return result;
	}

    @Override
    public PromotionExtendInfoDTO addDrawLotteryInfo(PromotionExtendInfoDTO promotionInfoEditReqDTO) {
        PromotionExtendInfoDTO rtobj = new PromotionExtendInfoDTO();
        try {
            if (promotionInfoEditReqDTO == null) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "促销活动参数不能为空");
            }
            if (StringUtils.isEmpty(promotionInfoEditReqDTO.getPromotionType())) {
            	throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_NO_TYPE.getCode(), "促销活动类型不能为空");
            }else{
	        	//---xuwei---
	        	if(promotionInfoEditReqDTO.getPromotionType().equals("21")){
	        		//扭蛋机
//	        		promotionInfoEditReqDTO.setPromotionType(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
//	        				DictionaryConst.OPT_PROMOTION_TYPE_GASHAPON));
	        		promotionInfoEditReqDTO.setIsDailyWinningLimit(1);
	        		promotionInfoEditReqDTO.setIsDailyTimesLimit(1);
	        		
	        	}else if(promotionInfoEditReqDTO.getPromotionType().equals("24")){
	        		//刮刮乐
//	        		promotionInfoEditReqDTO.setPromotionType(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
//	        				DictionaryConst.OPT_PROMOTION_TYPE_SCRATCH_CARD));
	        	}
            }
        	//---xuwei---
            if (StringUtils.isEmpty(promotionInfoEditReqDTO.getStatus())) {
                promotionInfoEditReqDTO.setStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS,
                        DictionaryConst.OPT_PROMOTION_STATUS_NO_START));
            }
            promotionInfoEditReqDTO.setShowStatus(dictionary
                    .getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                            DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID));
            Date itime = promotionInfoEditReqDTO.getInvalidTime();
            if (itime != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(itime);
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 59);
                promotionInfoEditReqDTO.setInvalidTime(cal.getTime());
            }
            // 判断时间段内可有活动上架
        	//---xuwei---
            Integer isUpPromotionFlag = promotionInfoDAO
                    .queryUpPromotionLotteryCount(null, promotionInfoEditReqDTO.getEffectiveTime(),
                            promotionInfoEditReqDTO.getInvalidTime(),promotionInfoEditReqDTO.getPromotionType());
            if (null != isUpPromotionFlag && isUpPromotionFlag.intValue() > 0) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_TIME_NOT_UP.getCode(),
                        "该活动有效期和其他活动重叠，请重新设置");
            }
        	//---xuwei---
            List<? extends PromotionAccumulatyDTO> plist = promotionInfoEditReqDTO.getPromotionAccumulatyList();
            if (plist != null && plist.size() > Integer
                    .parseInt(SysProperties.getProperty(PROMOTION_LOTTERY_MAX_AWARD_SIZE))) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_AWARD_NOT_CORRECT.getCode(),
                        "奖项设置已经到达最大值！");
            }
            int allq = 0;
            String qt = "";
            for (PromotionAccumulatyDTO promotionAccumulatyDTO : plist) {
                qt = promotionAccumulatyDTO.getLevelAmount();
                if (!StringUtils.isEmpty(qt)) {
                    allq = allq + Integer.parseInt(qt);
                }
            }
            if (allq != 100) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_AWARD_NOT_CORRECT.getCode(),
                        "设置的中奖概率之和不等于100%，活动无法提交，请重新设置！");
            }
            rtobj = promotionBaseService.insertPromotionInfo(promotionInfoEditReqDTO);
            if (rtobj.getPromotionAccumulatyList() != null) {
                List<? extends PromotionAccumulatyDTO> promotionAccumulatyList =
                        promotionInfoEditReqDTO.getPromotionAccumulatyList();
                PromotionAwardInfoDTO padrDTO = null;
                for (int i = 0; i < promotionAccumulatyList.size(); i++) {
                    padrDTO = (PromotionAwardInfoDTO) promotionAccumulatyList.get(i);
                    padrDTO.setPromotionId(rtobj.getPromotionId());
                    padrDTO.setCreateId(rtobj.getCreateId());
                    padrDTO.setCreateName(rtobj.getCreateName());
                    padrDTO.setModifyId(rtobj.getCreateId());
                    padrDTO.setModifyName(rtobj.getCreateName());
                    promotionAwardInfoDAO.add(padrDTO);
                }
                promotionLotteryCommonService.initPromotionLotteryRedisInfoWithThread(rtobj);
            }
            PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();
            historyDTO.setPromotionId(rtobj.getPromotionId());
            historyDTO.setPromotionStatus(
                    dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_STATUS, rtobj.getStatus()));
            historyDTO.setPromotionStatusText(
                    dictionary.getNameByValue(DictionaryConst.TYPE_PROMOTION_STATUS, rtobj.getStatus()));

            historyDTO.setCreateId(promotionInfoEditReqDTO.getCreateId());
            historyDTO.setCreateName(promotionInfoEditReqDTO.getCreateName());
            promotionStatusHistoryDAO.add(historyDTO);

            rtobj.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
            rtobj.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
        } catch (PromotionCenterBusinessException e) {
            rtobj.setResponseCode(e.getCode());
            rtobj.setResponseMsg(e.getMessage());
        } catch (Exception e) {
            rtobj.setResponseCode(ResultCodeEnum.ERROR.getCode());
            rtobj.setResponseMsg(ExceptionUtils.getStackTraceAsString(e));
        }

        return rtobj;
    }

    @Override
    public PromotionExtendInfoDTO editDrawLotteryInfo(PromotionExtendInfoDTO promotionInfoEditReqDTO) {
        PromotionExtendInfoDTO result = new PromotionExtendInfoDTO();
        try {
            if (promotionInfoEditReqDTO == null) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "促销活动参数不能为空");
            }
            if (StringUtils.isEmpty(promotionInfoEditReqDTO.getPromotionType())) {
            	throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_NO_TYPE.getCode(), "促销活动类型不能为空");
            }else{
	        	//---xuwei---
	        	if(promotionInfoEditReqDTO.getPromotionType().equals("21")){
	        		//扭蛋机
//	        		promotionInfoEditReqDTO.setPromotionType(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
//	        				DictionaryConst.OPT_PROMOTION_TYPE_GASHAPON));
	        		promotionInfoEditReqDTO.setIsDailyWinningLimit(1);
	        		promotionInfoEditReqDTO.setIsDailyTimesLimit(1);
	        		
	        	}else if(promotionInfoEditReqDTO.getPromotionType().equals("24")){
	        		//刮刮乐
//	        		promotionInfoEditReqDTO.setPromotionType(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_TYPE,
//	        				DictionaryConst.OPT_PROMOTION_TYPE_SCRATCH_CARD));
	        	}
            }
        	//---xuwei---
            // 判断时间段内可有活动上架
            Integer isUpPromotionFlag = promotionInfoDAO
                    .queryUpPromotionLotteryCount(promotionInfoEditReqDTO.getPromotionId(),
                            promotionInfoEditReqDTO.getEffectiveTime(), promotionInfoEditReqDTO.getInvalidTime(),promotionInfoEditReqDTO.getPromotionType());
            if (null != isUpPromotionFlag && isUpPromotionFlag.intValue() > 0) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_TIME_NOT_UP.getCode(),
                        "该活动有效期和其他活动重叠，请重新设置");
            }
            List<? extends PromotionAccumulatyDTO> plist = promotionInfoEditReqDTO.getPromotionAccumulatyList();
            if (plist != null && plist.size() > Integer
                    .parseInt(SysProperties.getProperty(PROMOTION_LOTTERY_MAX_AWARD_SIZE))) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_AWARD_NOT_CORRECT.getCode(),
                        "奖项设置已经到达最大值！");
            }
            int allq = 0;
            String qt = "";
            for (PromotionAccumulatyDTO promotionAccumulatyDTO : plist) {
                qt = promotionAccumulatyDTO.getLevelAmount();
                if (!StringUtils.isEmpty(qt)) {
                    allq = allq + Integer.parseInt(qt);
                }
            }
            if (allq != 100) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_AWARD_NOT_CORRECT.getCode(),
                        "设置的中奖概率之和不等于100%，活动无法提交，请重新设置！");
            }
            Date itime = promotionInfoEditReqDTO.getInvalidTime();
            if (itime != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(itime);
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 59);
                promotionInfoEditReqDTO.setInvalidTime(cal.getTime());
            }
            promotionInfoEditReqDTO.setShowStatus(dictionary
                    .getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                            DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID));
            result = promotionBaseService.updatePromotionInfo(promotionInfoEditReqDTO);
            if (result.getPromotionAccumulatyList() != null) {
                List<? extends PromotionAccumulatyDTO> promotionAccumulatyList =
                        promotionInfoEditReqDTO.getPromotionAccumulatyList();
                PromotionAwardInfoDTO padDTO = null;
				for (int i = 0; i < promotionAccumulatyList.size(); i++) {
					padDTO = (PromotionAwardInfoDTO) promotionAccumulatyList.get(i);
					padDTO.setPromotionId(result.getPromotionId());
					PromotionAwardInfoDTO pad = promotionAwardInfoDAO.queryByPIdAndLevel(padDTO);
					if (pad == null) {
						padDTO.setPromotionId(result.getPromotionId());
						padDTO.setCreateId(result.getModifyId());
						padDTO.setCreateName(result.getModifyName());
						padDTO.setModifyId(result.getModifyId());
						padDTO.setModifyName(result.getModifyName());
						promotionAwardInfoDAO.add(padDTO);
					} else {
						padDTO.setModifyId(result.getModifyId());
						padDTO.setModifyName(result.getModifyName());
						promotionAwardInfoDAO.update(padDTO);
					}
				}
			}
            promotionLotteryCommonService.initPromotionLotteryRedisInfoWithThread(result);
            //result = viewDrawLotteryInfo(promotionInfoEditReqDTO.getPromotionId());
            result.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
            result.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
        } catch (PromotionCenterBusinessException e) {
            result.setResponseCode(e.getCode());
            result.setResponseMsg(e.getMessage());
        } catch (Exception e) {
            result.setResponseCode(ResultCodeEnum.ERROR.getCode());
            result.setResponseMsg(ExceptionUtils.getStackTraceAsString(e));
        }

        return result;
    }

	@Override
	public PromotionExtendInfoDTO viewDrawLotteryInfo(String promotionInfoId) {
		PromotionExtendInfoDTO result = new PromotionExtendInfoDTO();
		try {

			result = (PromotionExtendInfoDTO) promotionBaseService
					.queryPromotionInfo(promotionInfoId);
			if (result.getPromotionAccumulatyList() != null) {
				List<? extends PromotionAccumulatyDTO> promotionAccumulatyList = result
						.getPromotionAccumulatyList();
				List<PromotionAwardInfoDTO> promotionAwardList = new ArrayList<PromotionAwardInfoDTO>();
				PromotionAccumulatyDTO padDTO = null;
				PromotionAwardInfoDTO pai = null;
				for (int i = 0; i < promotionAccumulatyList.size(); i++) {
					padDTO = promotionAccumulatyList.get(i);
					pai = new PromotionAwardInfoDTO();
					pai.setPromotionId(padDTO.getPromotionId());
					pai.setLevelCode(padDTO.getLevelCode());
					PromotionAwardInfoDTO pad = promotionAwardInfoDAO
							.queryByPIdAndLevel(pai);
					pad.setPromotionAccumulaty(padDTO);
					Long pvc = promotionRedisDB
							.getLlen(RedisConst.REDIS_LOTTERY_AWARD_PREFIX
									+ result.getPromotionId() + "_"
									+ pai.getLevelCode());
					if (pvc != null) {
						pad.setProvideCount(pvc.intValue());
					}
					promotionAwardList.add(pad);
				}
				result.setPromotionAccumulatyList(promotionAwardList);
			}
			result.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
			result.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
		} catch (PromotionCenterBusinessException e) {
			result.setResponseCode(e.getCode());
			result.setResponseMsg(e.getMessage());
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getCode());
			result.setResponseMsg(ExceptionUtils.getStackTraceAsString(e));
		}

		return result;
	}

	/**
	 * 查询正在进行的有效的促销活动id
	 * 
	 * @return
	 */
	public String queryEffectivePromotion(String currentPromotionType) {
		String promotionId = "";
		String promotionStatus = "";
		Date nowDate = new Date();
		String validStatus = dictionary.getValueByCode(
				DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
				DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID);
		Map<String, String> indexMap = promotionRedisDB
				.getHashOperations(RedisConst.REDIS_LOTTERY_INDEX);
		if (null != indexMap && !indexMap.isEmpty()) {
			for (Map.Entry<String, String> m : indexMap.entrySet()) {
				String key = m.getKey();
				String value = m.getValue();
				String[] keyArray = key.split("_");
				if (keyArray == null || keyArray.length < 2) {
					continue;
				}
				String[] valueArray = value.split("_");
				if (null == valueArray || valueArray.length < 2) {
					continue;
				}
				String promotionType = keyArray[0];
				promotionId = keyArray[1];
				promotionStatus = promotionRedisDB.getHash(
						RedisConst.REDIS_LOTTERY_VALID, promotionId);
				if (!validStatus.equals(promotionStatus)) {
					continue;
				}
				if (!currentPromotionType.equals(promotionType)) {
					continue;
				}
				Long startTime = new Long(valueArray[0]);
				Long endTime = new Long(valueArray[1]);
				Date stratDate = new Date(startTime);
				Date endDate = new Date(endTime);
				if (nowDate.after(stratDate) && nowDate.before(endDate)) {
					return promotionId;
				}
			}
		}
		return "";
	}
	
	/**
	 * 查询所有正在进行的有效的促销活动id List
	 * @return
	 */
	public List<String> queryAllEffectivePromotion(){
		List<String> promotionIdList = new ArrayList<String>(); 
		String promotionId = "";
		String promotionStatus = "";
		Date nowDate = new Date();
		String validStatus = dictionary.getValueByCode(
				DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
				DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID);
		Map<String, String> indexMap = promotionRedisDB
				.getHashOperations(RedisConst.REDIS_LOTTERY_INDEX);
		if (null != indexMap && !indexMap.isEmpty()) {
			for (Map.Entry<String, String> m : indexMap.entrySet()) {
				String key = m.getKey();
				String value = m.getValue();
				String[] keyArray = key.split("_");
				if (keyArray == null || keyArray.length < 2) {
					continue;
				}
				String[] valueArray = value.split("_");
				if (null == valueArray || valueArray.length < 2) {
					continue;
				}
				promotionId = keyArray[1];
				promotionStatus = promotionRedisDB.getHash(
						RedisConst.REDIS_LOTTERY_VALID, promotionId);
				if (!validStatus.equals(promotionStatus)) {
					continue;
				}
				Long startTime = new Long(valueArray[0]);
				Long endTime = new Long(valueArray[1]);
				Date stratDate = new Date(startTime);
				Date endDate = new Date(endTime);
				if (nowDate.after(stratDate) && nowDate.before(endDate)) {
					promotionIdList.add(promotionId);
				}
			}
		}
		return promotionIdList;
	}
	
	public static void main(String[] args) {
		Date nowDate = new Date();
		Long time = new Long("1517406643033");
		Date startTime = new Date(time);
		System.out.println(nowDate.after(startTime));

		String filed = "GASHAPON_1517406643033_1517406644033";
		String[] fieldArray = filed.split("_");
		System.out.println(fieldArray.length);
	}

	@Override
	public PromotionSellerRuleDTO participateActivitySellerInfo(String messageId) {
		PromotionSellerRuleDTO result = new PromotionSellerRuleDTO();
		try {
			List<String> promotionIdList = queryAllEffectivePromotion();
			if (CollectionUtils.isEmpty(promotionIdList)) {
				result.setResponseCode(ResultCodeEnum.LOTTERY_SELLER_NO_AUTHIORITY
						.getCode());
				result.setResponseMsg(ResultCodeEnum.LOTTERY_SELLER_NO_AUTHIORITY
						.getMsg());
				return result;
			}
			List<PromotionSellerDetailDTO> promotionSellerDetailDTOList = new ArrayList<PromotionSellerDetailDTO>();
			for(String promotionId : promotionIdList){
				Map<String, String> dictMap = null;
				PromotionExtendInfoDTO promotionInfoDTO = null;
				dictMap = baseService.initPromotionDictMap();
				promotionInfoDTO = promotionLotteryCommonService
						.getRedisLotteryInfo(promotionId, dictMap);
				if (null == promotionInfoDTO) {
					continue;
				}
				if (null != promotionInfoDTO.getSellerRuleDTO() && !CollectionUtils.isEmpty(promotionInfoDTO.getSellerRuleDTO().getSellerDetailList())) {
					result.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
					result.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
					promotionSellerDetailDTOList.addAll(promotionInfoDTO.getSellerRuleDTO().getSellerDetailList());
				}else{
					result.setResponseCode(ResultCodeEnum.LOTTERY_ALL_ORG_HAS_AUTHIORITY
							.getCode());
					result.setResponseMsg(ResultCodeEnum.LOTTERY_ALL_ORG_HAS_AUTHIORITY
							.getMsg());
					return result;
				}
			}
			
			if(ResultCodeEnum.SUCCESS.getCode().equals(result.getResponseCode())){
				Set<PromotionSellerDetailDTO> promotionSellerDetailDTOSet = new HashSet<PromotionSellerDetailDTO>();
				promotionSellerDetailDTOSet.addAll(promotionSellerDetailDTOList);
				promotionSellerDetailDTOList.clear();
				promotionSellerDetailDTOList.addAll(promotionSellerDetailDTOSet);
				result.setSellerDetailList(promotionSellerDetailDTOList);
			}else{
				result.setResponseCode(ResultCodeEnum.LOTTERY_NOT_HAS_PROMOTION_INFO
						.getCode());
				result.setResponseMsg(ResultCodeEnum.LOTTERY_NOT_HAS_PROMOTION_INFO
						.getMsg());
				return result;
			}
			
			/*Map<String, String> dictMap = null;
			PromotionExtendInfoDTO promotionInfoDTO = null;
			dictMap = baseService.initPromotionDictMap();
			promotionInfoDTO = promotionLotteryCommonService
					.getRedisLotteryInfo(promotionId, dictMap);
			if (null == promotionInfoDTO) {
				result.setResponseCode(ResultCodeEnum.LOTTERY_NOT_HAS_PROMOTION_INFO
						.getCode());
				result.setResponseMsg(ResultCodeEnum.LOTTERY_NOT_HAS_PROMOTION_INFO
						.getMsg());
				return result;
			}
			if (null != promotionInfoDTO.getSellerRuleDTO()) {
				result.setSellerDetailList(promotionInfoDTO.getSellerRuleDTO()
						.getSellerDetailList());
				result.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
				result.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
			} else {
				result.setResponseCode(ResultCodeEnum.LOTTERY_ALL_ORG_HAS_AUTHIORITY
						.getCode());
				result.setResponseMsg(ResultCodeEnum.LOTTERY_ALL_ORG_HAS_AUTHIORITY
						.getMsg());
			}*/
		} catch (PromotionCenterBusinessException bcbe) {
			result.setResponseCode(bcbe.getCode());
			result.setResponseMsg(bcbe.getMessage());
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getCode());
			result.setResponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法LuckDrawServiceImpl.participateActivitySellerInfo出现异常 request：{}异常信息：{}",
					messageId, messageId, w.toString());
		}
		return result;
	}

	@Override
	public void updateLotteryResultState(Map<String, Object> map) {
		BuyerWinningRecordDMO buyerWinningRecordDMO = new BuyerWinningRecordDMO();
		LOGGER.info("话费充值返回：" + JSON.toJSONString(map));
		if (map.get("rechargestatus") != null
				&& map.get("rechargestatus").equals("1")) {
			String order = (String) map.get("orderid");
			if (!StringUtils.isEmpty(order)) {
				String[] pid = order.split(":");
				if (pid != null && pid.length == 2) {
					buyerWinningRecordDMO.setDealFlag(0);
					// String promotionId = pid[0];
					// buyerWinningRecordDMO.setPromotionId(promotionId);
					buyerWinningRecordDMO.setId(new Long(pid[1]));
					buyerWinningRecordDAO.updateDealFlag(buyerWinningRecordDMO);
				}

			}
		}
	}

	@Override
	public ValidateScratchCardResDTO validateScratchCard(
			ValidateScratchCardReqDTO requestDTO) {
		String messageId = requestDTO.getMessageId();
		ValidateScratchCardResDTO result = new ValidateScratchCardResDTO();
		PromotionExtendInfoDTO promotionInfoDTO = null;
		Map<String, String> dictMap = null;
		try {
			result.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
			result.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
			String scratchCardType = dictionary.getValueByCode(
					DictionaryConst.TYPE_PROMOTION_TYPE,
					DictionaryConst.OPT_PROMOTION_TYPE_SCRATCH_CARD);
			String promotionId = queryEffectivePromotion(scratchCardType);
			if (StringUtils.isEmpty(promotionId)) {
				result.setResponseCode(ResultCodeEnum.PROMOTION_NOT_EXIST
						.getCode());
				result.setResponseMsg(ResultCodeEnum.PROMOTION_NOT_EXIST
						.getMsg());
				return result;
			}
			//校验
			promotionLotteryCommonService.checkScratchCardValid(
					promotionInfoDTO, requestDTO, dictMap);
			
			result.setPromotionId(promotionId);
		} catch (PromotionCenterBusinessException pcbe) {
			result.setResponseCode(pcbe.getCode());
			result.setResponseMsg(pcbe.getMessage());
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getCode());
			result.setResponseMsg(ResultCodeEnum.ERROR.getMsg());
			LOGGER.error(
					"MessageId:{} 调用方法LuckDrawServiceImpl.validateScratchCard出现异常 异常信息：{}",
					messageId, ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}

	@Override
	public LotteryActivityPageResDTO scratchCardActivityPage(
			ScratchCardActivityPageReqDTO requestDTO) {
		String messageId = requestDTO.getMessageId();
		LotteryActivityPageResDTO result = new LotteryActivityPageResDTO();
		try {
			//规则页
			String memberNo = requestDTO.getMemberNo();
			String orgId = requestDTO.getOrgId();
			String promotionId = requestDTO.getPromotionId();
			LotteryActivityPageReqDTO request = new LotteryActivityPageReqDTO();
			request.setMemberNo(memberNo);
			request.setOrgId(orgId);
			request.setPromotionId(promotionId);
			request.setMessageId(messageId);
			result = this.lotteryActivityPage(request);
			
			//判断是不是同一个人
			String oldMemberNo = requestDTO.getOldMemberNo();
			if (!oldMemberNo.equals(memberNo)) {
				throw new PromotionCenterBusinessException(
						ResultCodeEnum.LOTTERY_BUYER_NOT_HAVE_QUALIFICATIONS.getCode(),
						"还没有刮奖资格哦，赶紧下单来刮奖吧！ 入参:" + JSON.toJSONString(requestDTO));
			}else{
				String orderNo = requestDTO.getOrderNo();
				String buyerAwardInfo =  promotionId + "_" + orgId + "_" + memberNo + "_" + orderNo;
				if(!promotionRedisDB.existsHash(RedisConst.REDIS_LOTTERY_BUYER_AWARD_INFO,buyerAwardInfo)){
					boolean useThread = false;
					DrawLotteryReqDTO drawLotteryReqDTO = new DrawLotteryReqDTO();
					drawLotteryReqDTO.setMessageId(messageId);
					drawLotteryReqDTO.setBuyerCode(memberNo);
					drawLotteryReqDTO.setPromotionId(promotionId);
					drawLotteryReqDTO.setSellerCode(orgId);
					promotionLotteryService.beginDrawLotteryExecute(drawLotteryReqDTO, orderNo, useThread);
				}else{
					//判断orderNo是不是存在-如果存在就 “抱歉，这笔订单您已经刮过奖啦~请重新下单刮奖~”
					String recordJsonStr = promotionRedisDB.getHash(RedisConst.REDIS_LOTTERY_BUYER_AWARD_INFO,buyerAwardInfo);
					BuyerWinningRecordDTO winningRecordDTO = JSON.parseObject(recordJsonStr, BuyerWinningRecordDTO.class);
					String redisOrderNo = winningRecordDTO.getOrderNo();
					//如果redisOrderNo存在说明已经挂过奖了，如果不存在说明还没有刮奖
					if(!StringUtils.isEmpty(redisOrderNo)){
						throw new PromotionCenterBusinessException(
								ResultCodeEnum.LOTTERY_ORDER_HAD_LUCK_DRAW.getCode(),
								"抱歉，这笔订单您已经刮过奖啦~请重新下单刮奖~ 入参:" + JSON.toJSONString(requestDTO));
					}					
				}
			}
		}catch (PromotionCenterBusinessException pcbe) {
			result.setResponseCode(pcbe.getCode());
			result.setResponseMsg(pcbe.getMessage());
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getCode());
			result.setResponseMsg(ResultCodeEnum.ERROR.getMsg());
			LOGGER.error(
					"MessageId:{} 调用方法LuckDrawServiceImpl.scratchCardActivityPage出现异常 异常信息：{}",
					messageId, ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}
}
