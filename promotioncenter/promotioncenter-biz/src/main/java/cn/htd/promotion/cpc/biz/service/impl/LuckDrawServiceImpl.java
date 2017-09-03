package cn.htd.promotion.cpc.biz.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.biz.dao.BuyerWinningRecordDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionAwardInfoDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionDetailDescribeDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionStatusHistoryDAO;
import cn.htd.promotion.cpc.biz.dmo.BuyerWinningRecordDMO;
import cn.htd.promotion.cpc.biz.dmo.PromotionDetailDescribeDMO;
import cn.htd.promotion.cpc.biz.dmo.WinningRecordResDMO;
import cn.htd.promotion.cpc.biz.service.LuckDrawService;
import cn.htd.promotion.cpc.biz.service.PromotionBaseService;
import cn.htd.promotion.cpc.biz.service.PromotionLotteryCommonService;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.emums.PromotionCodeEnum;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.request.LotteryActivityPageReqDTO;
import cn.htd.promotion.cpc.dto.request.LotteryActivityRulePageReqDTO;
import cn.htd.promotion.cpc.dto.request.ShareLinkHandleReqDTO;
import cn.htd.promotion.cpc.dto.request.ValidateLuckDrawReqDTO;
import cn.htd.promotion.cpc.dto.request.WinningRecordReqDTO;
import cn.htd.promotion.cpc.dto.response.LotteryActivityPageResDTO;
import cn.htd.promotion.cpc.dto.response.LotteryActivityRulePageResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionAccumulatyDTO;
import cn.htd.promotion.cpc.dto.response.PromotionAwardInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionPictureDTO;
import cn.htd.promotion.cpc.dto.response.PromotionSellerRuleDTO;
import cn.htd.promotion.cpc.dto.response.ShareLinkHandleResDTO;
import cn.htd.promotion.cpc.dto.response.ValidateLuckDrawResDTO;

@Service("luckDrawService")
public class LuckDrawServiceImpl implements LuckDrawService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LuckDrawServiceImpl.class);

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
	private PromotionBaseService baseService;

	@Resource
	private PromotionLotteryCommonService promotionLotteryCommonService;

	@Override
	public ValidateLuckDrawResDTO validateLuckDrawPermission(
			ValidateLuckDrawReqDTO requestDTO) {
		String messageId = requestDTO.getMessageId();
		ValidateLuckDrawResDTO result = new ValidateLuckDrawResDTO();
		try {
			result.setResponseCode(ResultCodeEnum.LOTTERY_SELLER_NO_AUTHIORITY
					.getCode());
			result.setResponseMsg(ResultCodeEnum.LOTTERY_SELLER_NO_AUTHIORITY
					.getMsg());

			String orgId = requestDTO.getOrgId();
			String promotionId = queryEffectivePromotion();
			if (StringUtils.isEmpty(promotionId)) {
				return result;
			}

			Map<String, String> dictMap = null;
			PromotionExtendInfoDTO promotionInfoDTO = null;
			dictMap = baseService.initPromotionDictMap();
			promotionInfoDTO = promotionLotteryCommonService.getRedisLotteryInfo(
					promotionId, dictMap);
			if (baseService.checkPromotionSellerRule(promotionInfoDTO, orgId,
					dictMap)) {
				result.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
				result.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
				result.setPromotionId(promotionId);
				return result;
			}
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getCode());
			result.setResponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法LuckDrawServiceImpl.validateLuckDrawPermission出现异常 OrgId：{}",
					messageId, requestDTO.getOrgId(), w.toString());
		}
		return result;
	}

	@Override
	public LotteryActivityPageResDTO lotteryActivityPage(
			LotteryActivityPageReqDTO request) {
		String messageId = request.getMessageId();
		LotteryActivityPageResDTO result = new LotteryActivityPageResDTO();
		try {
			String promotionId = request.getPromotionId();
			// 抽奖活动信息
			String lotteryJson = promotionRedisDB.getHash(
					RedisConst.REDIS_LOTTERY_INFO, promotionId);
			PromotionExtendInfoDTO promotionExtendInfoDTO = JSON.parseObject(lotteryJson,PromotionExtendInfoDTO.class);
			List<PromotionPictureDTO> promotionPictureList = promotionExtendInfoDTO
					.getPromotionPictureList();
			List<String> pictureUrlList = null;
			if (CollectionUtils.isNotEmpty(promotionPictureList)) {
				pictureUrlList = new ArrayList<String>();
				for (PromotionPictureDTO promotionPicture : promotionPictureList) {
					pictureUrlList.add(promotionPicture
							.getPromotionPictureUrl());
				}
			}
			// 粉丝每日抽奖次数限制
			String buyerDailyDrawTimes = promotionRedisDB.getHash(
					RedisConst.REDIS_LOTTERY_TIMES_INFO + "_" + promotionId,
					RedisConst.REDIS_LOTTERY_BUYER_DAILY_DRAW_TIMES);

			Integer remainingTimes = Integer.valueOf(buyerDailyDrawTimes);
			result.setPictureUrl(pictureUrlList);
			result.setActivityStartTime(promotionExtendInfoDTO
					.getEachStartTime());
			result.setActivityEndTime(promotionExtendInfoDTO
					.getEachEndTime());
			result.setPromotionName(promotionExtendInfoDTO.getPromotionName());
			result.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
			result.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());

			String buyerNo = request.getMemberNo();
			if (StringUtils.isNotEmpty(buyerNo)) {
				result.setRemainingTimes(remainingTimes);
				// 粉丝活动粉丝当日次数信息
				String b2bMiddleLotteryBuyerTimesInfo = RedisConst.REDIS_LOTTERY_BUYER_TIMES_INFO
						+ "_" + buyerNo;

				List<String> buyerTimeInfoList = promotionRedisDB
						.getHashFields(b2bMiddleLotteryBuyerTimesInfo);
				if (CollectionUtils.isEmpty(buyerTimeInfoList)) {
					// 粉丝活动粉丝当日剩余参与次数
					promotionRedisDB.setHash(b2bMiddleLotteryBuyerTimesInfo,
							RedisConst.REDIS_LOTTERY_BUYER_PARTAKE_TIMES,
							remainingTimes.toString());
					// 粉丝当日中奖次数
					promotionRedisDB.setHash(b2bMiddleLotteryBuyerTimesInfo,
							RedisConst.REDIS_LOTTERY_BUYER_WINNING_TIMES, "0");
					// 粉丝分享次数
					promotionRedisDB.setHash(b2bMiddleLotteryBuyerTimesInfo,
							RedisConst.REIDS_LOTTERY_BUYER_SHARE_TIMES, "0");
					// 粉丝已经达到分享获得抽奖次数上限
					promotionRedisDB.setHash(b2bMiddleLotteryBuyerTimesInfo,
							RedisConst.REDIS_LOTTERY_BUYER_HAS_TOP_EXTRA_TIMES,
							PromotionCodeEnum.BUYER_HAS_TOP_EXTRA_TIMES
									.getCode());
				}
				// TODO 校验
			}
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getCode());
			result.setResponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法LuckDrawServiceImpl.lotteryActivityPage出现异常 request：{}",
					messageId, JSONObject.toJSONString(request), w.toString());
		}
		return result;
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
					"MessageId:{} 调用方法LuckDrawServiceImpl.lotteryActivityRulePage出现异常 request：{}",
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
			// 粉丝每日抽奖次数限制
			String buyerDailyDrawTimes = promotionRedisDB.getHash(
					lotteryTimesInfo,
					RedisConst.REDIS_LOTTERY_BUYER_DAILY_DRAW_TIMES);
			// 每次分享获得抽奖次数
			String buyerShareExtraPartakeTimes = promotionRedisDB.getHash(
					lotteryTimesInfo,
					RedisConst.REDIS_LOTTERY_BUYER_SHARE_EXTRA_PARTAKE_TIMES);
			// 粉丝分享获得抽奖次数上限
			String buyerTopExtraPartakeTime = promotionRedisDB.getHash(
					lotteryTimesInfo,
					RedisConst.REDIS_LOTTERY_BUYER_TOP_EXTRA_PARTAKE_TIMES);

			String buyerCode = request.getMemberNo();
			String lotteryBuyerTimes = RedisConst.REDIS_LOTTERY_BUYER_TIMES_INFO
					+ "_" + buyerCode;
			// 粉丝分享次数
			promotionRedisDB.incrHash(lotteryBuyerTimes,
					RedisConst.REIDS_LOTTERY_BUYER_SHARE_TIMES);
			Long partakeTime = Long.valueOf(buyerShareExtraPartakeTimes);
			if (StringUtils.isEmpty(buyerTopExtraPartakeTime)) {
				// 粉丝活动粉丝当日参与次数--总共剩余参与次数
				promotionRedisDB.incrHashBy(lotteryBuyerTimes,
						RedisConst.REDIS_LOTTERY_BUYER_PARTAKE_TIMES,
						partakeTime);
			} else {
				// 粉丝活动粉丝当日参与次数--总共参与次数
				Long totalTimes = Long.valueOf(buyerDailyDrawTimes)
						+ Long.valueOf(buyerTopExtraPartakeTime);
				if (promotionRedisDB.incrHashBy(lotteryBuyerTimes,
						RedisConst.REDIS_LOTTERY_BUYER_PARTAKE_TIMES,
						partakeTime) > totalTimes) {
					promotionRedisDB.incrHashBy(lotteryBuyerTimes,
							RedisConst.REDIS_LOTTERY_BUYER_PARTAKE_TIMES,
							-partakeTime);
				}
			}
			// 更新粉丝抽奖次数成功
			result.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getCode());
			result.setResponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法LuckDrawServiceImpl.shareLinkHandle出现异常 request：{}",
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
					"MessageId:{} 调用方法LuckDrawServiceImpl.queryWinningRecord出现异常 request：{}",
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
			//promotionInfoEditReqDTO.setPromotionType("NDJ");
			rtobj = promotionBaseService.insertPromotionInfo(promotionInfoEditReqDTO);
			if(rtobj.getPromotionAccumulatyList()!=null){
				List<? extends PromotionAccumulatyDTO> promotionAccumulatyList = promotionInfoEditReqDTO.getPromotionAccumulatyList();
				PromotionAwardInfoDTO padrDTO = null;
				for (int i = 0; i < promotionAccumulatyList.size(); i++) {
		            padrDTO = (PromotionAwardInfoDTO)promotionAccumulatyList.get(i);
		            padrDTO.setPromotionId(rtobj.getPromotionId());
		            promotionAwardInfoDAO.add(padrDTO);
				}
				promotionLotteryCommonService.initPromotionLotteryRedisInfoWithThread(rtobj);
			}
//			PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();
//			historyDTO.setPromotionId(rtobj.getPromotionId());
//            historyDTO.setPromotionStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
//                    DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_PENDING));
//            historyDTO.setPromotionStatusText(dictionary.getNameByValue(
//                    DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
//                    DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_PENDING));
//            historyDTO.setCreateId(promotionInfoEditReqDTO.getCreateId());
//            historyDTO.setCreateName(promotionInfoEditReqDTO.getCreateName());
//            promotionStatusHistoryDAO.add(historyDTO);
            
			rtobj.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
			rtobj.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
		} catch (PromotionCenterBusinessException e) {
			rtobj.setResponseCode(ResultCodeEnum.ERROR.getCode());
			rtobj.setResponseMsg(ResultCodeEnum.ERROR.getMsg());
		} catch (Exception e) {
			rtobj.setResponseCode(ResultCodeEnum.ERROR.getCode());
			rtobj.setResponseMsg(ResultCodeEnum.ERROR.getMsg());
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
			result = promotionBaseService.updatePromotionInfo(promotionInfoEditReqDTO);
			if(result.getPromotionAccumulatyList()!=null){
				  List<? extends PromotionAccumulatyDTO> promotionAccumulatyList = promotionInfoEditReqDTO.getPromotionAccumulatyList();
				PromotionAwardInfoDTO padDTO = null;
				for (int i = 0; i < promotionAccumulatyList.size(); i++) {
		            padDTO = (PromotionAwardInfoDTO)promotionAccumulatyList.get(i);
		            promotionAwardInfoDAO.update(padDTO);
				}
				promotionLotteryCommonService.initPromotionLotteryRedisInfoWithThread(result);
			}
//			PromotionStatusHistoryDTO historyDTO = new PromotionStatusHistoryDTO();
//			historyDTO.setPromotionId(rtobj.getPromotionId());
//            historyDTO.setPromotionStatus(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
//                    DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_PENDING));
//            historyDTO.setPromotionStatusText(dictionary.getNameByValue(
//                    DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
//                    DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_PENDING));
//            historyDTO.setCreateId(promotionInfoEditReqDTO.getCreateId());
//            historyDTO.setCreateName(promotionInfoEditReqDTO.getCreateName());
//            promotionStatusHistoryDAO.add(historyDTO);
			result.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
			result.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
		} catch (PromotionCenterBusinessException e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getCode());
			result.setResponseMsg(ResultCodeEnum.ERROR.getMsg());
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getCode());
			result.setResponseMsg(ResultCodeEnum.ERROR.getMsg());
		}

		return result;
	}

	@Override
	public PromotionExtendInfoDTO viewDrawLotteryInfo(String promotionInfoId) {
		PromotionExtendInfoDTO  result = new PromotionExtendInfoDTO();
		try {

			result = (PromotionExtendInfoDTO) promotionBaseService.queryPromotionInfo(promotionInfoId);
			if(result.getPromotionAccumulatyList()!=null){
				  List<? extends PromotionAccumulatyDTO> promotionAccumulatyList = result.getPromotionAccumulatyList();
				  List<PromotionAwardInfoDTO> promotionAwardList = new ArrayList<PromotionAwardInfoDTO>();
				PromotionAccumulatyDTO padDTO = null;
				PromotionAwardInfoDTO pai = null;
				for (int i = 0; i < promotionAccumulatyList.size(); i++) {
		            padDTO = promotionAccumulatyList.get(i);
		            pai = new PromotionAwardInfoDTO();
		            pai.setPromotionId(padDTO.getPromotionId());
		            pai.setLevelCode(padDTO.getLevelCode());
		            PromotionAwardInfoDTO pad = promotionAwardInfoDAO.queryByPIdAndLevel(pai);
		            pad.setPromotionAccumulaty(padDTO);
		            promotionAwardList.add(pad);
				}
				result.setPromotionAccumulatyList(promotionAwardList);
			}
			result.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
			result.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
		} catch (PromotionCenterBusinessException e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getCode());
			result.setResponseMsg(ResultCodeEnum.ERROR.getMsg());
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getCode());
			result.setResponseMsg(ResultCodeEnum.ERROR.getMsg());
		}

		return result;
	}

	/**
	 * 查询正在进行的有效的促销活动id
	 * 
	 * @return
	 */
	public String queryEffectivePromotion() {
		String promotionId = "";
		String b2bMiddleLotteryIndex = RedisConst.REDIS_LOTTERY_INDEX;
		Map<String,String> indexMap = promotionRedisDB.getHashOperations(b2bMiddleLotteryIndex);
		if(null != indexMap && !indexMap.isEmpty()){
			Date nowDate = new Date();
			for(Map.Entry<String,String> m : indexMap.entrySet()){
				String field = m.getKey();
				String[] fieldArray = field.split("_");
				if (null != fieldArray && fieldArray.length > 2) {
					Long startTime = new Long(fieldArray[1]);
					Long endTime = new Long(fieldArray[2]);
					Date stratDate = new Date(startTime);
					Date endDate = new Date(endTime);
					if (nowDate.after(stratDate) && nowDate.before(endDate)) {
						promotionId = m.getValue();
						if (StringUtils.isNotEmpty(promotionId)) {
							// 验证抽奖活动的有效状态
							String promotionStatus = promotionRedisDB
									.getHash(RedisConst.REDIS_LOTTERY_VALID,
											promotionId);
							if (PromotionCodeEnum.LOTTERY_EFFECTIVE.getCode()
									.equals(promotionStatus))
								return promotionId;
						}
					}
				}
			}
		}
		return promotionId;
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
			String promotionId = queryEffectivePromotion();
			if (StringUtils.isEmpty(promotionId)) {
				result.setResponseCode(ResultCodeEnum.LOTTERY_SELLER_NO_AUTHIORITY.getCode());
				result.setResponseMsg(ResultCodeEnum.LOTTERY_SELLER_NO_AUTHIORITY.getMsg());
				return result;
			}
			Map<String, String> dictMap = null;
			PromotionExtendInfoDTO promotionInfoDTO = null;
			dictMap = baseService.initPromotionDictMap();
			promotionInfoDTO = promotionLotteryCommonService.getRedisLotteryInfo(
					promotionId, dictMap);
			result.setSellerDetailList(promotionInfoDTO.getSellerRuleDTO().getSellerDetailList());
		} catch (Exception e) {
			result.setResponseCode(ResultCodeEnum.ERROR.getCode());
			result.setResponseMsg(ResultCodeEnum.ERROR.getMsg());
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error(
					"MessageId:{} 调用方法LuckDrawServiceImpl.participateActivitySellerInfo出现异常 request：{}",
					messageId, messageId, w.toString());
		}
		return result;
	}
}
