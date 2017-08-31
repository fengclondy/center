package cn.htd.promotion.cpc.biz.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.htd.promotion.cpc.biz.dao.AwardRecordDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionDetailDescribeDAO;
import cn.htd.promotion.cpc.biz.dmo.BuyerWinningRecordDMO;
import cn.htd.promotion.cpc.biz.dmo.PromotionDetailDescribeDMO;
import cn.htd.promotion.cpc.biz.dmo.WinningRecordResDMO;
import cn.htd.promotion.cpc.biz.service.LuckDrawService;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.emums.PromotionCodeEnum;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.request.LotteryActivityPageReqDTO;
import cn.htd.promotion.cpc.dto.request.LotteryActivityRulePageReqDTO;
import cn.htd.promotion.cpc.dto.request.ShareLinkHandleReqDTO;
import cn.htd.promotion.cpc.dto.request.ValidateLuckDrawReqDTO;
import cn.htd.promotion.cpc.dto.request.WinningRecordReqDTO;
import cn.htd.promotion.cpc.dto.response.LotteryActivityPageResDTO;
import cn.htd.promotion.cpc.dto.response.LotteryActivityRulePageResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionPictureDTO;
import cn.htd.promotion.cpc.dto.response.ShareLinkHandleResDTO;
import cn.htd.promotion.cpc.dto.response.ValidateLuckDrawResDTO;

@Service("luckDrawService")
public class LuckDrawServiceImpl implements LuckDrawService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LuckDrawServiceImpl.class);

	@Resource
	private AwardRecordDAO awardRecordDAO;

	@Resource
	private PromotionDetailDescribeDAO promotionDetailDescribeDAO;

	@Resource
	private PromotionRedisDB promotionRedisDB;

	@Override
	public ValidateLuckDrawResDTO validateLuckDrawPermission(
			ValidateLuckDrawReqDTO requestDTO) {
		String messageId = requestDTO.getMessageId();
		ValidateLuckDrawResDTO result = new ValidateLuckDrawResDTO();
		try {
			result.setResponseCode(ResultCodeEnum.LUCK_DRAW_NOT_HAVE_DRAW_PERMISSION
					.getCode());
			result.setResponseMsg(ResultCodeEnum.LUCK_DRAW_NOT_HAVE_DRAW_PERMISSION
					.getMsg());

			String orgId = requestDTO.getOrgId();
			String promotionIds = promotionRedisDB.getHash(
					RedisConst.REDIS_LOTTERY_INDEX,
					RedisConst.REDIS_GASHAPON_PREFIX + orgId);
			if (StringUtils.isNotEmpty(promotionIds)) {
				String[] promotionArray = promotionIds.split(",");
				if (null != promotionArray && promotionArray.length > 0) {
					for (int i = 0; i < promotionArray.length; i++) {
						String promotionId = promotionArray[i];
						String gashaponStatus = promotionRedisDB.getHash(
								RedisConst.REDIS_LOTTERY_VALID, promotionId);
						if (PromotionCodeEnum.LOTTERY_EFFECTIVE.getCode()
								.equals(gashaponStatus)) {
							result.setResponseCode(ResultCodeEnum.SUCCESS
									.getCode());
							result.setResponseMsg(ResultCodeEnum.SUCCESS
									.getMsg());
							result.setPromotionId(promotionId);
							return result;
						}
					}
				}
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
			JSONObject jsonObject = JSON.parseObject(lotteryJson);
			PromotionExtendInfoDTO promotionExtendInfoDTO = jsonObject
					.toJavaObject(jsonObject, PromotionExtendInfoDTO.class);
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
			String buyerNo = request.getMemberNo();
			// 粉丝活动粉丝当日参与次数
			String buyerPartakeTimes = promotionRedisDB.getHash(
					RedisConst.REDIS_LOTTERY_BUYER_TIMES_INFO + "_" + buyerNo,
					RedisConst.REDIS_LOTTERY_BUYER_PARTAKE_TIMES);
			Integer remainingTimes = null;
			if (StringUtils.isNotEmpty(buyerDailyDrawTimes)) {
				remainingTimes = Integer.valueOf(buyerDailyDrawTimes)
						- Integer.valueOf(buyerPartakeTimes == null ? "0"
								: buyerPartakeTimes);
			}
			result.setRemainingTimes(remainingTimes);
			result.setPictureUrl(pictureUrlList);
			result.setActivityStartTime(promotionExtendInfoDTO
					.getOfflineStartTime());
			result.setActivityEndTime(promotionExtendInfoDTO
					.getOfflineEndTime());
			result.setPromotionName(promotionExtendInfoDTO.getPromotionName());
			result.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
			result.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
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
				result.setResponseCode(ResultCodeEnum.PROMOTION_LOTTERY_NO_DESCRIBE_CONTENT
						.getCode());
				result.setResponseMsg(ResultCodeEnum.PROMOTION_LOTTERY_NO_DESCRIBE_CONTENT
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
			String lotteryBuyerTimes = RedisConst.REDIS_LOTTERY_BUYER_TIMES_INFO+"_"+buyerCode;
			//粉丝分享次数
			promotionRedisDB.incrHash(lotteryBuyerTimes,RedisConst.REIDS_LOTTERY_BUYER_SHARE_TIMES);
			Long partakeTime = Long.valueOf(buyerShareExtraPartakeTimes);
			if(StringUtils.isEmpty(buyerTopExtraPartakeTime)){
				//粉丝活动粉丝当日参与次数--总共参与次数
				promotionRedisDB.incrHashBy(lotteryBuyerTimes, RedisConst.REDIS_LOTTERY_BUYER_PARTAKE_TIMES,partakeTime);
			}else{
				//粉丝活动粉丝当日参与次数--总共参与次数
				Long totalTimes = Long.valueOf(buyerDailyDrawTimes)+Long.valueOf(buyerTopExtraPartakeTime);
				if(promotionRedisDB.incrHashBy(lotteryBuyerTimes, RedisConst.REDIS_LOTTERY_BUYER_PARTAKE_TIMES,partakeTime)>totalTimes){
					promotionRedisDB.incrHashBy(lotteryBuyerTimes, RedisConst.REDIS_LOTTERY_BUYER_PARTAKE_TIMES,-partakeTime);
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
}
