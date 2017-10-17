package cn.htd.promotion.cpc.biz.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.constant.DictionaryConst;
import cn.htd.promotion.cpc.biz.service.PromotionBaseService;
import cn.htd.promotion.cpc.biz.service.PromotionLotteryCommonService;
import cn.htd.promotion.cpc.biz.service.PromotionLotteryService;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.GeneratorUtils;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.dto.request.DrawLotteryReqDTO;
import cn.htd.promotion.cpc.dto.request.DrawLotteryResultReqDTO;
import cn.htd.promotion.cpc.dto.request.DrawLotteryWinningReqDTO;
import cn.htd.promotion.cpc.dto.request.ScratchCardDrawLotteryReqDTO;
import cn.htd.promotion.cpc.dto.response.BuyerWinningRecordDTO;
import cn.htd.promotion.cpc.dto.response.DrawLotteryResDTO;
import cn.htd.promotion.cpc.dto.response.GenricResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;

import com.alibaba.fastjson.JSON;

@Service("promotionLotteryService")
public class PromotionLotteryServiceImpl implements PromotionLotteryService {

    private static final Logger logger = LoggerFactory.getLogger(PromotionLotteryServiceImpl.class);

    @Resource
    private GeneratorUtils noGenerator;

    @Resource
    private PromotionBaseService baseService;

    @Resource
    private PromotionRedisDB promotionRedisDB;

    @Resource
    private PromotionLotteryCommonService promotionLotteryCommonService;

    /**
     * 开始抽奖处理
     *
     * @param requestDTO
     * @return
     * @throws PromotionCenterBusinessException
     * @throws Exception
     */
    @Override
    public DrawLotteryResDTO beginDrawLottery(DrawLotteryReqDTO requestDTO)
            throws PromotionCenterBusinessException, Exception {
        DrawLotteryResDTO responseDTO = new DrawLotteryResDTO();
        String buyerCode = requestDTO.getBuyerCode();
        String sellerCode = requestDTO.getSellerCode();
        String promotionId = requestDTO.getPromotionId();
        String ticket = noGenerator.generateLotteryTicket(promotionId + sellerCode + buyerCode);
        boolean useSync = requestDTO.isUseSync();
        responseDTO = this.beginDrawLotteryExecute(requestDTO, ticket, useSync);
        return responseDTO;
    }

    /**
     * 开始抽奖处理4刮刮卡
     *
     * @param requestDTO
     * @return
     * @throws PromotionCenterBusinessException
     * @throws Exception
     */
    @Override
    public DrawLotteryResDTO beginDrawLotteryScratchCard(ScratchCardDrawLotteryReqDTO requestDTO)
            throws PromotionCenterBusinessException, Exception {
        DrawLotteryResDTO responseDTO = new DrawLotteryResDTO();
        String buyerCode = requestDTO.getBuyerCode();
        String sellerCode = requestDTO.getSellerCode();
        String promotionId = requestDTO.getPromotionId();
        String ticket = requestDTO.getOrderNo();
        boolean useSync = requestDTO.isUseSync();
        
        //如果从redis里查不到订单信息，说明是从非法途径(比如浏览器输入地址)进入的
        String field = promotionId+"_"+sellerCode+"_"+buyerCode+"_"+ticket;
		boolean orderExist = promotionRedisDB.existsHash(RedisConst.REDIS_LOTTERY_B2C_MIDDLE_LOTTERY_ORDER_INFO, field);
		if(!orderExist){
			throw new PromotionCenterBusinessException(
					ResultCodeEnum.LOTTERY_ORDER_WRONGFUL.getCode(),
					"抱歉，抽奖订单不合法(通过非法途径抽奖)~ 入参:" + JSON.toJSONString(requestDTO));
		}
        
        //校验是否已经挂过奖了,如果已经挂过了且报存了中奖信息，告诉前端已经挂过了，如果没有填写中奖信息，返回ticket
        String buyerAwardInfo =  promotionId + "_" + sellerCode + "_" + buyerCode + "_" + ticket;
		if(promotionRedisDB.existsHash(RedisConst.REDIS_LOTTERY_BUYER_AWARD_INFO,buyerAwardInfo)){
			//判断orderNo是不是存在-如果存在就 “抱歉，这笔订单您已经刮过奖啦~请重新下单刮奖~”
			String recordJsonStr = promotionRedisDB.getHash(RedisConst.REDIS_LOTTERY_BUYER_AWARD_INFO,buyerAwardInfo);
			BuyerWinningRecordDTO winningRecordDTO = JSON.parseObject(recordJsonStr, BuyerWinningRecordDTO.class);
			String redisOrderNo = winningRecordDTO.getOrderNo();
			//如果redisOrderNo存在说明已经挂过奖了，如果不存在说明还没有刮奖
			if(!StringUtils.isEmpty(redisOrderNo)){
				throw new PromotionCenterBusinessException(
						ResultCodeEnum.LOTTERY_ORDER_HAD_LUCK_DRAW.getCode(),
						"抱歉，这笔订单您已经刮过奖啦~请重新下单刮奖~ 入参:" + JSON.toJSONString(requestDTO));
			}else{
				responseDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
				responseDTO.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
				responseDTO.setTicket(ticket);
				return responseDTO;
			}					
		}
        
        DrawLotteryReqDTO drawLotteryReqDTO = new DrawLotteryReqDTO();
        drawLotteryReqDTO.setBuyerCode(buyerCode);
        drawLotteryReqDTO.setMessageId(requestDTO.getMessageId());
        drawLotteryReqDTO.setOrderNo(requestDTO.getOrderNo());
        drawLotteryReqDTO.setPromotionId(promotionId);
        drawLotteryReqDTO.setSellerCode(sellerCode);
        drawLotteryReqDTO.setUseSync(useSync);
        responseDTO = this.beginDrawLotteryExecute(drawLotteryReqDTO, ticket, useSync);
        return responseDTO;
    }
    
    /**
     * 执行抽奖处理
     *
     * @param requestDTO
     * @param ticket
     * @param useThread
     * @return
     * @throws PromotionCenterBusinessException
     * @throws Exception
     */
    @Override
    public DrawLotteryResDTO beginDrawLotteryExecute(DrawLotteryReqDTO requestDTO, String ticket, boolean useSync)
            throws PromotionCenterBusinessException, Exception {
        DrawLotteryResDTO responseDTO = new DrawLotteryResDTO();
        String buyerCode = requestDTO.getBuyerCode();
        String sellerCode = requestDTO.getSellerCode();
        String promotionId = requestDTO.getPromotionId();
        Map<String, String> dictMap = null;
        PromotionExtendInfoDTO promotionInfoDTO = null;
        BuyerWinningRecordDTO errorWinningRecord = new BuyerWinningRecordDTO();

        responseDTO.setMessageId(requestDTO.getMessageId());
        responseDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
        responseDTO.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
        dictMap = baseService.initPromotionDictMap();
        promotionInfoDTO = promotionLotteryCommonService.getRedisLotteryInfo(promotionId, dictMap);
        if (promotionLotteryCommonService.checkBuyerPromotionLotteryValid(promotionInfoDTO, requestDTO, dictMap)) {
            errorWinningRecord.setBuyerWinningRecordByPromoitonInfo(promotionInfoDTO);
            errorWinningRecord.setBuyerCode(buyerCode);
            errorWinningRecord.setSellerCode(sellerCode);
            errorWinningRecord.setRewardType("0");
            responseDTO.setTicket(ticket);
            //使用异步线程处理
            if (useSync) {
            	//使用同步处理
                promotionLotteryCommonService.doDrawLottery(requestDTO, errorWinningRecord, ticket);
            } else {
            	promotionLotteryCommonService.doDrawLotteryWithThread(requestDTO, errorWinningRecord, ticket);
            }
        }
        return responseDTO;
    }

    /**
     * 查询抽奖结果处理
     *
     * @param requestDTO
     * @return
     * @throws PromotionCenterBusinessException
     * @throws Exception
     */
    @Override
    public BuyerWinningRecordDTO getDrawLotteryResult(DrawLotteryResultReqDTO requestDTO)
            throws PromotionCenterBusinessException, Exception {
        final String buyerCode = requestDTO.getBuyerCode();
        final String sellerCode = requestDTO.getSellerCode();
        final String promotionId = requestDTO.getPromotionId();
        final String ticket = requestDTO.getTicket();
        String recordJsonStr = "";
        BuyerWinningRecordDTO responseDTO = new BuyerWinningRecordDTO();
        long partakeTimes = 0L;

        responseDTO.setResponseCode(ResultCodeEnum.LOTTERY_NO_RESULT.getCode());
        responseDTO.setResponseMsg(ResultCodeEnum.LOTTERY_NO_RESULT.getMsg());
        if (promotionRedisDB.existsHash(RedisConst.REDIS_LOTTERY_BUYER_AWARD_INFO,
                promotionId + "_" + sellerCode + "_" + buyerCode + "_" + ticket)) {
            recordJsonStr = promotionRedisDB.getHash(RedisConst.REDIS_LOTTERY_BUYER_AWARD_INFO,
                    promotionId + "_" + sellerCode + "_" + buyerCode + "_" + ticket);
            responseDTO = JSON.parseObject(recordJsonStr, BuyerWinningRecordDTO.class);
            if (responseDTO == null) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.ERROR.getCode(),
                        "抽奖结果异常 " + recordJsonStr + " 入参:" + JSON.toJSONString(requestDTO));
            }
            partakeTimes = Long.valueOf(promotionRedisDB
                    .getHash(RedisConst.REDIS_LOTTERY_BUYER_TIMES_INFO + "_" + promotionId + "_" + buyerCode,
                            RedisConst.REDIS_LOTTERY_BUYER_PARTAKE_TIMES));
            responseDTO.setRemainLotteryChance(partakeTimes < 0 ? 0 : partakeTimes);
            final BuyerWinningRecordDTO checkWinningRecord = responseDTO;
            new Thread() {
                public void run() {
                    if (!ResultCodeEnum.SUCCESS.getCode().equals(checkWinningRecord.getResponseCode())) {
                        promotionRedisDB.delHash(RedisConst.REDIS_LOTTERY_BUYER_AWARD_INFO,
                                promotionId + "_" + sellerCode + "_" + buyerCode + "_" + ticket);
                    }
                }
            }.start();
        }
        responseDTO.setMessageId(requestDTO.getMessageId());
        return responseDTO;
    }

    /**
     * 保存中奖结果信息
     *
     * @param requestDTO
     * @return
     * @throws PromotionCenterBusinessException
     * @throws Exception
     */
    @Override
    public GenricResDTO saveDrawLotteryWinning(DrawLotteryWinningReqDTO requestDTO)
            throws PromotionCenterBusinessException, Exception {
        String buyerCode = requestDTO.getBuyerCode();
        String sellerCode = requestDTO.getSellerCode();
        String promotionId = requestDTO.getPromotionId();
        String ticket = requestDTO.getTicket();
        String recordJsonStr = "";
        GenricResDTO responseDTO = new GenricResDTO();
        BuyerWinningRecordDTO winningRecordDTO = null;
        Map<String, String> dictMap = new HashMap<String, String>();
        String promotionType = "";

        responseDTO.setMessageId(requestDTO.getMessageId());
        responseDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
        responseDTO.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());
        if (!promotionRedisDB.existsHash(RedisConst.REDIS_LOTTERY_BUYER_AWARD_INFO,
                promotionId + "_" + sellerCode + "_" + buyerCode + "_" + ticket)) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.LOTTERY_BUYER_NO_WINNING_RECORD.getCode(),
                    "粉丝没有中奖记录 入参:" + JSON.toJSONString(requestDTO));
        }
        recordJsonStr = promotionRedisDB.getHash(RedisConst.REDIS_LOTTERY_BUYER_AWARD_INFO,
                promotionId + "_" + sellerCode + "_" + buyerCode + "_" + ticket);
        winningRecordDTO = JSON.parseObject(recordJsonStr, BuyerWinningRecordDTO.class);
        if (winningRecordDTO == null) {
            throw new PromotionCenterBusinessException(ResultCodeEnum.ERROR.getCode(),
                    "抽奖结果异常 " + recordJsonStr + " 入参:" + JSON.toJSONString(requestDTO));
        }
        if (!ResultCodeEnum.SUCCESS.getCode().equals(winningRecordDTO.getResponseCode())) {
            return responseDTO;
        }
        baseService.initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_REWARD_TYPE);
        if (dictMap.get(DictionaryConst.TYPE_PROMOTION_REWARD_TYPE + "&"
                + DictionaryConst.OPT_PROMOTION_REWARD_TYPE_PRACTICALITY).equals(winningRecordDTO.getRewardType())) {
            if (StringUtils.isEmpty(requestDTO.getWinnerName())) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "中奖人姓名不能为空");
            }
            if (StringUtils.isEmpty(requestDTO.getWinningContact())) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "中奖人联系方式不能为空");
            }
        } else if (dictMap.get(DictionaryConst.TYPE_PROMOTION_REWARD_TYPE + "&"
                + DictionaryConst.OPT_PROMOTION_REWARD_TYPE_TEL_RECHARGE).equals(winningRecordDTO.getRewardType())) {
            if (StringUtils.isEmpty(requestDTO.getWinnerName())) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "中奖人姓名不能为空");
            }
            if (StringUtils.isEmpty(requestDTO.getChargeTelephone())) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "充值手机号码不能为空");
            }
            if (!Pattern.matches("^1[34578]\\d{9}$", requestDTO.getChargeTelephone())) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(), "充值手机号码不正确");
            }
        }
        winningRecordDTO.setBuyerName(requestDTO.getBuyerName());
        winningRecordDTO.setBuyerTelephone(requestDTO.getBuyerTelephone());
        winningRecordDTO.setSellerName(requestDTO.getSellerName());
        winningRecordDTO.setSellerAddress(requestDTO.getSellerAddress());
        winningRecordDTO.setBelongSuperiorName(requestDTO.getBelongsSuperiorName());
        winningRecordDTO.setWinnerName(requestDTO.getWinnerName());
        winningRecordDTO.setWinningContact(requestDTO.getWinningContact());
        winningRecordDTO.setChargeTelephone(requestDTO.getChargeTelephone());
        winningRecordDTO.setCreateId(0L);
        winningRecordDTO.setCreateName(requestDTO.getBuyerName());
        winningRecordDTO.setOrderNo(ticket);
        promotionRedisDB
                .tailPush(RedisConst.REDIS_BUYER_WINNING_RECORD_NEED_SAVE_LIST, JSON.toJSONString(winningRecordDTO));
        //如果是扭蛋就删除抽奖结果key，如果是刮刮乐就不删除，等刮刮乐活动结束用定时任务删除
        promotionType = winningRecordDTO.getPromotionType();
        baseService.initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_TYPE);
        if (dictMap.get(DictionaryConst.TYPE_PROMOTION_TYPE + "&" + DictionaryConst.OPT_PROMOTION_TYPE_GASHAPON)
                .equals(promotionType)) {
            promotionRedisDB.delHash(RedisConst.REDIS_LOTTERY_BUYER_AWARD_INFO,
                    promotionId + "_" + sellerCode + "_" + buyerCode + "_" + ticket);
        } else if (dictMap
                .get(DictionaryConst.TYPE_PROMOTION_TYPE + "&" + DictionaryConst.OPT_PROMOTION_TYPE_SCRATCH_CARD)
                .equals(promotionType)) {
            promotionRedisDB.setHash(RedisConst.REDIS_LOTTERY_BUYER_AWARD_INFO,
                    promotionId + "_" + sellerCode + "_" + buyerCode + "_" + ticket,
                    JSON.toJSONString(winningRecordDTO));
        }
        responseDTO.setMessageId(requestDTO.getMessageId());
        return responseDTO;
    }
    /**
     * 保存红包雨信息
     *
     * @param requestDTO
     * @return
     * @throws PromotionCenterBusinessException
     * @throws Exception
     */
	public GenricResDTO saveRedRainWinningInfo(
			DrawLotteryWinningReqDTO requestDTO)
			throws PromotionCenterBusinessException, Exception {
        String ticket = requestDTO.getTicket();
        String relevanceCouponCode = requestDTO.getRelevanceCouponCode();
        GenricResDTO responseDTO = new GenricResDTO();
        BuyerWinningRecordDTO winningRecordDTO = new BuyerWinningRecordDTO();
        Map<String, String> dictMap = new HashMap<String, String>();

        responseDTO.setMessageId(requestDTO.getMessageId());
        responseDTO.setResponseCode(ResultCodeEnum.SUCCESS.getCode());
        responseDTO.setResponseMsg(ResultCodeEnum.SUCCESS.getMsg());

        baseService.initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_REWARD_TYPE);
        winningRecordDTO.setPromotionType(requestDTO.getPromotionType());
        winningRecordDTO.setPromotionId(requestDTO.getPromotionId());
        winningRecordDTO.setPromotionName(requestDTO.getPromotionName());
        winningRecordDTO.setBuyerCode(requestDTO.getBuyerCode());
        winningRecordDTO.setSellerCode(requestDTO.getSellerCode());
        winningRecordDTO.setBuyerName(requestDTO.getBuyerName());
        winningRecordDTO.setBuyerTelephone(requestDTO.getBuyerTelephone());
        winningRecordDTO.setSellerName(requestDTO.getSellerName());
        winningRecordDTO.setSellerAddress(requestDTO.getSellerAddress());
        winningRecordDTO.setBelongSuperiorName(requestDTO.getBelongsSuperiorName());
        winningRecordDTO.setWinnerName(requestDTO.getWinnerName());
        winningRecordDTO.setWinningContact(requestDTO.getWinningContact());
        winningRecordDTO.setChargeTelephone(requestDTO.getChargeTelephone());
        winningRecordDTO.setCreateId(0L);
        winningRecordDTO.setCreateName(requestDTO.getBuyerName());
        winningRecordDTO.setOrderNo(ticket);
        winningRecordDTO.setRelevanceCouponCode(relevanceCouponCode);
        promotionRedisDB
                .tailPush(RedisConst.REDIS_BUYER_WINNING_RECORD_NEED_SAVE_LIST, JSON.toJSONString(winningRecordDTO));
//        //如果是扭蛋就删除抽奖结果key，如果是刮刮乐就不删除，等刮刮乐活动结束用定时任务删除
//        promotionType = winningRecordDTO.getPromotionType();
//        baseService.initDictionaryMap(dictMap, DictionaryConst.TYPE_PROMOTION_TYPE);
//        if (dictMap.get(DictionaryConst.TYPE_PROMOTION_TYPE + "&" + DictionaryConst.OPT_PROMOTION_TYPE_GASHAPON)
//                .equals(promotionType)) {
//            promotionRedisDB.delHash(RedisConst.REDIS_LOTTERY_BUYER_AWARD_INFO,
//                    promotionId + "_" + sellerCode + "_" + buyerCode + "_" + ticket);
//        } else if (dictMap
//                .get(DictionaryConst.TYPE_PROMOTION_TYPE + "&" + DictionaryConst.OPT_PROMOTION_TYPE_SCRATCH_CARD)
//                .equals(promotionType)) {
//            promotionRedisDB.setHash(RedisConst.REDIS_LOTTERY_BUYER_AWARD_INFO,
//                    promotionId + "_" + sellerCode + "_" + buyerCode + "_" + ticket,
//                    JSON.toJSONString(winningRecordDTO));
//        }
        responseDTO.setMessageId(requestDTO.getMessageId());
        return responseDTO;
	}
}
