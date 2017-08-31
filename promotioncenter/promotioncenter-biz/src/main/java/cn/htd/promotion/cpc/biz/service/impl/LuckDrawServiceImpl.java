package cn.htd.promotion.cpc.biz.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.promotion.cpc.biz.dao.AwardRecordDAO;
import cn.htd.promotion.cpc.biz.dmo.BuyerWinningRecordDMO;
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
import cn.htd.promotion.cpc.dto.response.ShareLinkHandleResDTO;
import cn.htd.promotion.cpc.dto.response.ValidateLuckDrawResDTO;

import com.alibaba.fastjson.JSONObject;

@Service("luckDrawService")
public class LuckDrawServiceImpl implements LuckDrawService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LuckDrawServiceImpl.class);
	
	@Resource
    AwardRecordDAO awardRecordDAO;
	
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
			String promotionIds = promotionRedisDB.getHash(RedisConst.REDIS_LOTTERY_INDEX, RedisConst.REDIS_GASHAPON_PREFIX+orgId);
			if(StringUtils.isNotEmpty(promotionIds)){
				String[] promotionArray = promotionIds.split(",");
				if(null != promotionArray && promotionArray.length>0){
					for(int i=0;i<promotionArray.length;i++){
						String promotionId = promotionArray[i];
						String gashaponStatus = promotionRedisDB.getHash(RedisConst.REDIS_LOTTERY_VALID, promotionId);
						if(PromotionCodeEnum.LOTTERY_EFFECTIVE.getCode().equals(gashaponStatus)){
							result.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
							result.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
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
			// TODO
			result.setPromotionName("");
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
		try{
			//TODO 
			//promotionInfoDAO 从数据库里查出活动规则
			result.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
			result.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
		}catch(Exception e){
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
	public ShareLinkHandleResDTO shareLinkHandle(
			ShareLinkHandleReqDTO request) {
		String messageId = request.getMessageId();
		ShareLinkHandleResDTO result = new ShareLinkHandleResDTO();
		try {
			//TODO
			//更新粉丝抽奖次数成功
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
	public WinningRecordResDMO queryWinningRecord(
			WinningRecordReqDTO request) {
		String messageId = request.getMessageId();
		WinningRecordResDMO result = new WinningRecordResDMO();
		try{
			BuyerWinningRecordDMO buyerWinningRecordDMO = new BuyerWinningRecordDMO();
			buyerWinningRecordDMO.setBuyerCode(request.getMemberNo());
			//页面传入的开始位置减一
			Integer startNo = request.getStartNo()==null?new Integer(0):request.getStartNo()-1;
			Integer endNo = request.getEndNo()==null?new Integer(10):request.getEndNo();
			buyerWinningRecordDMO.setStartNo(startNo);
			buyerWinningRecordDMO.setEndNo(endNo);
			List<BuyerWinningRecordDMO> winningRecordList = awardRecordDAO.queryWinningRecord(buyerWinningRecordDMO);
			result.setWinningRecordList(winningRecordList);
			result.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
			result.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
		}catch (Exception e) {
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
