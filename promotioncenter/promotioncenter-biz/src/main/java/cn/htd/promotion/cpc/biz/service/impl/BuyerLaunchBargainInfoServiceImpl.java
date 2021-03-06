package cn.htd.promotion.cpc.biz.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.htd.common.DataGrid;
import cn.htd.common.Pager;
import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.biz.dao.BuyerBargainRecordDAO;
import cn.htd.promotion.cpc.biz.dao.BuyerLaunchBargainInfoDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionBargainInfoDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionInfoDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionInfoExtendDAO;
import cn.htd.promotion.cpc.biz.dmo.BuyerLaunchBargainInfoDMO;
import cn.htd.promotion.cpc.biz.dmo.PromotionBargainInfoDMO;
import cn.htd.promotion.cpc.biz.handle.PromotionBargainRedisHandle;
import cn.htd.promotion.cpc.biz.service.BuyerLaunchBargainInfoService;
import cn.htd.promotion.cpc.common.constants.Constants;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.ExceptionUtils;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.common.util.GeneratorUtils;
import cn.htd.promotion.cpc.common.util.PromotionRedisDB;
import cn.htd.promotion.cpc.common.util.ValidateResult;
import cn.htd.promotion.cpc.common.util.ValidationUtils;
import cn.htd.promotion.cpc.dto.request.BuyerBargainLaunchReqDTO;
import cn.htd.promotion.cpc.dto.request.BuyerBargainRecordReqDTO;
import cn.htd.promotion.cpc.dto.response.BuyerLaunchBargainInfoResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBargainInfoResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionExtendInfoDTO;
import cn.htd.promotion.cpc.dto.response.PromotionInfoDTO;

@Service("buyerLaunchBargainInfoService")
public class BuyerLaunchBargainInfoServiceImpl implements BuyerLaunchBargainInfoService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BuyerLaunchBargainInfoServiceImpl.class);
	
	@Resource
	private BuyerLaunchBargainInfoDAO buyerLaunchBargainInfoDAO;
	
    @Resource
    private PromotionInfoDAO promotionInfoDAO;
    
    @Resource
	private PromotionRedisDB promotionRedisDB;
    
    @Resource
    private DictionaryUtils dictionary;
    
	@Resource
	private GeneratorUtils noGenerator;
	
	@Resource
	private PromotionBargainRedisHandle promotionBargainRedisHandle;
	
	@Resource
	private BuyerBargainRecordDAO buyerBargainRecordDAO;
	
	@Resource
	private PromotionInfoExtendDAO promotionInfoExtendDAO;
	
    @Resource
    private PromotionBargainInfoDAO promotionBargainInfoDAO;
	
	@Override
	public List<BuyerLaunchBargainInfoResDTO> getBuyerLaunchBargainInfoByBuyerCode(String buyerCode,String messageId) {
		LOGGER.info("MessageId{}:调用buyerLaunchBargainInfoDAO.getBuyerLaunchBargainInfoByBuyerCode（）方法开始,入参{}",messageId,buyerCode+":"+messageId);
		List<BuyerLaunchBargainInfoDMO> buyerBargainInfoList = buyerLaunchBargainInfoDAO.getBuyerLaunchBargainInfoByBuyerCode(buyerCode);
		LOGGER.info("MessageId{}:调用buyerLaunchBargainInfoDAO.getBuyerLaunchBargainInfoByBuyerCode（）方法开始,出参{}",messageId,
				JSON.toJSONString(buyerBargainInfoList));
//		for(BuyerLaunchBargainInfoDMO buyerL : buyerBargainInfoList){
//			// 从redis里面获取砍价详情
//			List<PromotionBargainInfoResDTO> promotionBargainInfoResDTOList = promotionBargainRedisHandle
//					.getRedisBargainInfoList(buyerL.getPromotionId());
//			if (promotionBargainInfoResDTOList != null
//					&& promotionBargainInfoResDTOList.size() > 0) {
//				for (PromotionBargainInfoResDTO p : promotionBargainInfoResDTOList) {
//					buyerL.setBargainPersonNum(p.getVirtualQuantity());
//					break;
//				}
//			}
//		}
		List<BuyerLaunchBargainInfoResDTO> buyerLaunchBargainInfoResList = new ArrayList<BuyerLaunchBargainInfoResDTO>();
		if(buyerBargainInfoList != null){
			String str = JSONObject.toJSONString(buyerBargainInfoList);
			buyerLaunchBargainInfoResList = JSONObject.parseArray(str,BuyerLaunchBargainInfoResDTO.class);
		}
		return buyerLaunchBargainInfoResList;
	}

	@Override
	public List<BuyerLaunchBargainInfoResDTO> getBuyerLaunchBargainInfoByPromotionId(
			String promotionId, String messageId) {
		LOGGER.info("MessageId{}:调用buyerLaunchBargainInfoDAO.getBuyerLaunchBargainInfoByPromotionId（）方法开始,入参{}",messageId,promotionId+":"+messageId);
		List<BuyerLaunchBargainInfoDMO> buyerBargainInfoList = buyerLaunchBargainInfoDAO.getBuyerLaunchBargainInfoByPromotionId(promotionId, null);
		LOGGER.info("MessageId{}:调用buyerLaunchBargainInfoDAO.getBuyerLaunchBargainInfoByPromotionId（）方法开始,出参{}",messageId,JSON.toJSONString(buyerBargainInfoList));
		List<BuyerLaunchBargainInfoResDTO> buyerLaunchBargainInfoResList = new ArrayList<BuyerLaunchBargainInfoResDTO>();
		if(buyerBargainInfoList != null){
			String str = JSONObject.toJSONString(buyerBargainInfoList);
			buyerLaunchBargainInfoResList = JSONObject.parseArray(str,BuyerLaunchBargainInfoResDTO.class);
		}
		return buyerLaunchBargainInfoResList;
	}

	@Override
	public ExecuteResult<BuyerLaunchBargainInfoResDTO> addBuyerBargainLaunch(BuyerLaunchBargainInfoResDTO bargainInfoDTO, String messageId) 
				throws PromotionCenterBusinessException {
		LOGGER.info("MessageId{}:调用buyerLaunchBargainInfoDAO.addBuyerBargainLaunch（）方法开始,入参{}",messageId,JSON.toJSONString(bargainInfoDTO)+":"+messageId);
		ExecuteResult<BuyerLaunchBargainInfoResDTO> result = new ExecuteResult<BuyerLaunchBargainInfoResDTO>();
		PromotionInfoDTO promotionInfo = null;
		PromotionExtendInfoDTO promotionInfoExtend = null;
		BuyerBargainLaunchReqDTO paramDTO = new BuyerBargainLaunchReqDTO();
		try {
			if(StringUtils.isNotEmpty(bargainInfoDTO.getPromotionId()) && StringUtils.isNotEmpty(bargainInfoDTO.getLevelCode())){
				paramDTO.setPromotionId(bargainInfoDTO.getPromotionId());
				paramDTO.setLevelCode(bargainInfoDTO.getLevelCode());
				PromotionBargainInfoDMO paramDMO = promotionBargainInfoDAO.queryPromotionBargainInfo(paramDTO);
				if(null == paramDMO){
	                throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_NOT_EXIST.getCode(), "砍价活动不存在");
				}
				bargainInfoDTO.setGoodsName(paramDMO.getGoodsName());
				bargainInfoDTO.setGoodsCostPrice(paramDMO.getGoodsCostPrice());
				bargainInfoDTO.setGoodsFloorPrice(paramDMO.getGoodsFloorPrice());
				bargainInfoDTO.setPartakeTimes(paramDMO.getPartakeTimes());
				bargainInfoDTO.setGoodsNum(paramDMO.getGoodsNum());
				bargainInfoDTO.setGoodsPicture(paramDMO.getGoodsPicture());
			}
			// 输入DTO的验证
			ValidateResult validateResult = ValidationUtils.validateEntity(bargainInfoDTO);
			if (validateResult.isHasErrors()) {
				throw new PromotionCenterBusinessException(ResultCodeEnum.PARAMETER_ERROR.getCode(),
	                  validateResult.getErrorMsg());
			}
			promotionInfo = promotionInfoDAO.queryById(bargainInfoDTO.getPromotionId());
			if (promotionInfo == null) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_NOT_EXIST.getCode(), "砍价活动不存在");
            }
			String promotionType = promotionInfo.getShowStatus();
			if(!dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                    DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID).equals(promotionType)){
				throw new PromotionCenterBusinessException(ResultCodeEnum.BARGAIN_NOT_VALID.getCode(),
		                  "砍价活动未启用");
			}
			if((new Date()).before(promotionInfo.getEffectiveTime())){
				throw new PromotionCenterBusinessException(ResultCodeEnum.BARGAIN_NOT_VALID.getCode(),
		                  "砍价活动时间未开始");
			}
			if((new Date()).after(promotionInfo.getInvalidTime())){
				throw new PromotionCenterBusinessException(ResultCodeEnum.BARGAIN_NOT_UNAVAILABLE.getCode(),
		                  "砍价活动时间已结束");
			}
			promotionInfoExtend = promotionInfoExtendDAO.queryById(bargainInfoDTO.getPromotionId());
			if(promotionInfoExtend == null) {
                throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_NOT_EXIST.getCode(), "砍价活动不存在");

			}
			Integer launchTimes = buyerLaunchBargainInfoDAO.queryBuyerLaunchBargainInfoNumber(bargainInfoDTO);
			if(null != launchTimes && launchTimes.intValue() > 0) {
				throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_BARGAIN_JOIN_QTY.getCode(),
		                  "该砍价活动商品只能发起一次流程");
			}
			List<BuyerLaunchBargainInfoDMO> launchList = buyerLaunchBargainInfoDAO.getBuyerLaunchBargainInfoByPromotionId(promotionInfo.getPromotionId(), bargainInfoDTO.getBuyerCode());
			if(null != launchList && !launchList.isEmpty() 
					&& null != promotionInfoExtend.getTotalPartakeTimes()
					&& launchList.size() >= promotionInfoExtend.getTotalPartakeTimes().intValue()){
				throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_BARGAIN_JOIN_QTY.getCode(),
		                  "该砍价活动商品参与次数已上限");
			}
			BuyerLaunchBargainInfoResDTO stockDTO = new BuyerLaunchBargainInfoResDTO();
			stockDTO.setPromotionId(bargainInfoDTO.getPromotionId());
			stockDTO.setLevelCode(bargainInfoDTO.getLevelCode());
			stockDTO.setIsBargainOver(1);
			Integer bargainLockingStockNumber = buyerLaunchBargainInfoDAO.queryBuyerLaunchBargainInfoNumber(stockDTO);
			if(null != bargainLockingStockNumber && bargainLockingStockNumber.intValue() >= bargainInfoDTO.getGoodsNum()){
				throw new PromotionCenterBusinessException(ResultCodeEnum.PROMOTION_NO_STOCK.getCode(),
		                  "砍价商品库存不足");
			}
			bargainInfoDTO.setIsBargainOver(0);
			bargainInfoDTO.setBargainCode(noGenerator.generatePromotionGargainLaunchCode(promotionType));
			BigDecimal popPrice = promotionBargainRedisHandle.addRedisBargainPriceSplit(bargainInfoDTO);
			if(null == popPrice){
				throw new PromotionCenterBusinessException(ResultCodeEnum.POMOTION_SPLIT_PRICE_ERROR.getCode(),
		                  "拆分金额失败");
			}
			BigDecimal goodsCurrentPrice = bargainInfoDTO.getGoodsCostPrice().subtract(popPrice);
			bargainInfoDTO.setGoodsCurrentPrice(goodsCurrentPrice);
			buyerLaunchBargainInfoDAO.addBuyerLaunchBargainInfo(bargainInfoDTO);
			//插入砍价记录表
			BuyerBargainRecordReqDTO buyerBargainRecord = new BuyerBargainRecordReqDTO();
			buyerBargainRecord.setBargainCode(bargainInfoDTO.getBargainCode());
			buyerBargainRecord.setHeadSculptureUrl(bargainInfoDTO.getHeadSculptureURL());
			buyerBargainRecord.setBargainPersonCode(bargainInfoDTO.getBargainCode());
			buyerBargainRecord.setBargainPresonName(bargainInfoDTO.getBuyerName());
			buyerBargainRecord.setBargainAmount(popPrice);
			buyerBargainRecord.setCreateId(bargainInfoDTO.getCreateId());
			buyerBargainRecord.setCreateName(bargainInfoDTO.getCreateName());
			buyerBargainRecord.setCreateTime(new Date());
			buyerBargainRecord.setBargainTime(new Date());
//			promotionRedisDB.tailPush(RedisConst.BUYER_BARGAIN_RECORD, JSON.toJSONString(buyerBargainRecord));//从右边插入队列
			buyerBargainRecordDAO.insertBuyerBargainRecord(buyerBargainRecord);
			result.setResult(bargainInfoDTO);
		} catch (PromotionCenterBusinessException pbs){
			result.setCode(pbs.getCode());
			result.setErrorMessage(pbs.getMessage());
		}catch (Exception e) {
            result.setCode(ResultCodeEnum.ERROR.getCode());
            result.setErrorMessage(e.toString());
        }
		return result;
	}
	
	public Integer updateBuyerLaunchBargainInfo(BuyerBargainLaunchReqDTO buyerBargainLaunch) {
		LOGGER.info("MessageId{}:调用buyerLaunchBargainInfoDAO.updateBuyerLaunchBargainInfo（）方法开始,入参{}",buyerBargainLaunch.getMessageId(),
				JSON.toJSONString(buyerBargainLaunch));
		Integer flag = buyerLaunchBargainInfoDAO.updateBuyerLaunchBargainInfo(buyerBargainLaunch);
		LOGGER.info("MessageId{}:调用buyerLaunchBargainInfoDAO.updateBuyerLaunchBargainInfo（）方法开始,出参{}",buyerBargainLaunch.getMessageId(),
				"执行结果为："+flag);
		return flag;
	}
	
	@Override
	public BuyerLaunchBargainInfoResDTO getBuyerBargainLaunchInfoByBargainCode(String bargainCode, String messageId) {
		BuyerLaunchBargainInfoResDTO buyerLaunchBargainInfoResDTO = null;
		LOGGER.info("MessageId{}:调用buyerLaunchBargainInfoDAO.getBuyerBargainLaunchInfoByBargainCode（）方法开始,入参{}",
				messageId,bargainCode+":"+messageId);
		BuyerLaunchBargainInfoDMO buyerBargainInfo = buyerLaunchBargainInfoDAO.getBuyerBargainLaunchInfoByBargainCode(bargainCode);
		LOGGER.info("MessageId{}:调用buyerLaunchBargainInfoDAO.getBuyerBargainLaunchInfoByBargainCode（）方法开始,出参{}",messageId,
				JSON.toJSONString(buyerBargainInfo));
		if(buyerBargainInfo != null){
			String str = JSONObject.toJSONString(buyerBargainInfo);
			buyerLaunchBargainInfoResDTO = JSONObject.parseObject(str,BuyerLaunchBargainInfoResDTO.class);
		}
		return buyerLaunchBargainInfoResDTO;
	}

	@Override
	public Integer getBuyerLaunchBargainInfoNum(String promotionId, String levelCode,
			String messageId) {
		LOGGER.info("MessageId{}:调用buyerLaunchBargainInfoDAO.getBuyerLaunchBargainInfoList（）方法开始,入参{}",
				messageId,promotionId+":"+levelCode+":"+messageId);
		BuyerLaunchBargainInfoResDTO buyerLaunchBargainInfo = new BuyerLaunchBargainInfoResDTO();
		buyerLaunchBargainInfo.setPromotionId(promotionId);
		buyerLaunchBargainInfo.setLevelCode(levelCode);
		buyerLaunchBargainInfo.setIsBargainOver(1);
		LOGGER.info("MessageId{}:调用buyerLaunchBargainInfoDAO.queryBuyerLaunchBargainInfoNumber（）方法开始,入参{}",messageId,
				JSON.toJSONString(buyerLaunchBargainInfo));
		Integer i = buyerLaunchBargainInfoDAO.queryBuyerLaunchBargainInfoNumber(buyerLaunchBargainInfo);
		LOGGER.info("MessageId{}:调用buyerLaunchBargainInfoDAO.getBuyerLaunchBargainInfoList（）方法结束,出参{}",messageId,
				i);
		return i;
	}
	
	@Override
	public ExecuteResult<DataGrid<BuyerLaunchBargainInfoResDTO>> queryLaunchBargainInfoList(
			BuyerBargainLaunchReqDTO buyerBargainLaunch, Pager<BuyerBargainLaunchReqDTO> page) {
		LOGGER.info("MessageId{}:调用buyerLaunchBargainInfoDAO.queryLaunchBargainInfoList（）方法开始,入参{}", JSON.toJSONString(buyerBargainLaunch));
		DataGrid<BuyerLaunchBargainInfoResDTO> dataGrid = new DataGrid<BuyerLaunchBargainInfoResDTO>();
		ExecuteResult<DataGrid<BuyerLaunchBargainInfoResDTO>> result = new ExecuteResult<DataGrid<BuyerLaunchBargainInfoResDTO>>();
		try {
			List<BuyerLaunchBargainInfoDMO> launchBargainList = buyerLaunchBargainInfoDAO.queryLaunchBargainInfoList(buyerBargainLaunch, page);
			if(null != launchBargainList && !launchBargainList.isEmpty()){
				String bargainDMOStr = JSON.toJSONString(launchBargainList);
				List<BuyerLaunchBargainInfoResDTO> dtoList = JSONObject.parseArray(bargainDMOStr, BuyerLaunchBargainInfoResDTO.class);
				Long launchBargainCount = buyerLaunchBargainInfoDAO.queryLaunchBargainInfoCount(buyerBargainLaunch);
				dataGrid.setRows(dtoList);
				dataGrid.setTotal(launchBargainCount);
				result.setResult(dataGrid);
			}
		} catch (Exception e) {
			result.setCode(ResultCodeEnum.ERROR.getCode());
            result.setErrorMessage(e.toString());
		}
		return result;
	}

	@Override
	public ExecuteResult<String> updateBargainRecord(String promotionId, String bargainCode) {
		LOGGER.info("MessageId{}:调用BuyerLaunchBargainInfoServiceImpl.updateBargainRecord（）方法开始,入参{}{}", promotionId, bargainCode);
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			buyerLaunchBargainInfoDAO.updateBargainRecord(promotionId, bargainCode);
		} catch (Exception e) {
			result.setCode(ResultCodeEnum.ERROR.getCode());
			result.setErrorMessage(e.toString());
		}
		return result;
	}
	
	@Override
	public ExecuteResult<String> optationbargain(String buyerCode, String promotionId, String levelCode,
			String bargainCode, String helperPicture, String helperName, String openedId, String messageId) {
		ExecuteResult<String> result = new ExecuteResult<String>();
		try {
			String key = RedisConst.REDIS_BARGAIN_PRICE_SPLIT + "_" + promotionId + "_" + levelCode + "_" + bargainCode;
			String stockKey = RedisConst.REDIS_BARGAIN_ITEM_STOCK + "_" + promotionId + "_" + levelCode;

			// 当前的砍价商品
			PromotionBargainInfoDMO promotionBargainInfo = null;
			// 从redis里面获取砍价详情
			PromotionBargainInfoResDTO dto = new PromotionBargainInfoResDTO();
			dto.setPromotionId(promotionId);
			List<PromotionBargainInfoResDTO> promotionBargainInfoResDTOList = promotionBargainRedisHandle
					.getRedisBargainInfoList(dto);
			if (promotionBargainInfoResDTOList != null && promotionBargainInfoResDTOList.size() > 0) {
				for (PromotionBargainInfoResDTO p : promotionBargainInfoResDTOList) {
					if (levelCode.equals(p.getLevelCode())) {
						promotionBargainInfo = new PromotionBargainInfoDMO();
						promotionBargainInfo.setPromotionId(p.getPromotionId());
						promotionBargainInfo.setLevelCode(p.getLevelCode());
						promotionBargainInfo.setGoodsPicture(p.getGoodsPicture());
						promotionBargainInfo.setGoodsName(p.getGoodsName());
						promotionBargainInfo.setGoodsCostPrice(p.getGoodsCostPrice());
						promotionBargainInfo.setGoodsFloorPrice(p.getGoodsFloorPrice());
						promotionBargainInfo.setGoodsNum(p.getGoodsNum());
						promotionBargainInfo.setPartakeTimes(p.getPartakeTimes());
						promotionBargainInfo.setPromotionDesc(p.getPromotionSlogan());
						promotionBargainInfo.setContactNameD(p.getContactName());
						promotionBargainInfo.setContactTelphoneD(p.getContactTelephone());
						promotionBargainInfo.setContactAddressD(p.getContactAddress());
						promotionBargainInfo.setOfflineStartTimeD(p.getOfflineStartTime());
						promotionBargainInfo.setOfflineEndTimeD(p.getOfflineEndTime());
						promotionBargainInfo.setTemplateFlagD(p.getTemplateFlag());
						promotionBargainInfo.setSellerNameD(p.getPromotionProviderSellerCode());
						promotionBargainInfo.setEffectiveTime(p.getEffectiveTime());
						promotionBargainInfo.setInvalidTime(p.getInvalidTime());
						promotionBargainInfo.setShowStatusD(p.getShowStatus());
						break;
					}
				}
			}
			if (promotionBargainInfo != null) {
				// 判断是否已经砍完
				if (!promotionRedisDB.exists(key)) {
					result.setCode(Constants.PROMOTION_PATYIESIN_IS_EXCEED);
					result.setErrorMessage("参与砍价人数超过上限");
					result.setResult(openedId);
					return result;
				}
				
				// 判断砍价是否过期
				Long effectiveTime = promotionBargainInfo.getEffectiveTime().getTime();
				Long invalidTime = promotionBargainInfo.getInvalidTime().getTime();
				Long currentTime = (new Date()).getTime();
				if (currentTime > invalidTime) {// 当前时间大于结束时间说明已经结束
					result.setCode(Constants.PROMOTION_DATETIME_IS_OVER);
					result.setErrorMessage("该促销活动已经结束");
					result.setResult(openedId);
					return result;
				}

				// 判断砍价是否开始
				if (currentTime < effectiveTime) {// 当前时间小于开始时间说明活动还未开始
					result.setCode(Constants.PROMOTION_DATETIME_IS_NOSTART);
					result.setErrorMessage("该促销活动还未开始");
					result.setResult(openedId);
					return result;
				}
				
				// 判断砍价是否下架
				if ("4".equals(promotionBargainInfo.getShowStatusD())) {// 下架状态
					result.setCode(Constants.IS_DOWN_SELF);
					result.setErrorMessage("该促销活动已经下架");
					result.setResult(openedId);
					return result;
				}

				// 判断当前砍价人是否参与过砍价
				String s = promotionRedisDB.getHash(Constants.IS_BUYER_BARGAIN + promotionId,
						openedId + promotionId + levelCode + bargainCode);
				if (!StringUtils.isEmpty(s)) {// 没有参与过砍价
					result.setCode(Constants.PROMOTION_IS_PATYIESIN);
					result.setErrorMessage("该粉丝已经参与过砍价，不能重复参与");
					result.setResult(openedId);
					return result;
				}

				String bargainPrice = promotionRedisDB.headPop(key);// 砍的价格
				if (StringUtils.isEmpty(bargainPrice)) {
					result.setCode(Constants.PROMOTION_IS_BARGAIN_OVER);
					result.setErrorMessage("该商品已经砍完");
					result.setResult(openedId);
					return result;
				}

				// 获取该商品砍完之后的价格
				BuyerBargainLaunchReqDTO buyerBargainLaunchReqDTO = new BuyerBargainLaunchReqDTO();
				buyerBargainLaunchReqDTO.setGoodsCurrentPrice(new BigDecimal(bargainPrice));
				buyerBargainLaunchReqDTO.setMessageId(messageId);
				buyerBargainLaunchReqDTO.setModifyId(111);
				buyerBargainLaunchReqDTO.setModifyName(helperName);
				buyerBargainLaunchReqDTO.setModifyTime(new Date());
				buyerBargainLaunchReqDTO.setPromotionId(promotionId);
				buyerBargainLaunchReqDTO.setLevelCode(levelCode);
				buyerBargainLaunchReqDTO.setBargainCode(bargainCode);
				// 队列长度为0或者队列为不存在的时候则说明已经砍完
				if (!promotionRedisDB.exists(key)) {
					buyerBargainLaunchReqDTO.setBargainOverTime(new Date());
					buyerBargainLaunchReqDTO.setIsBargainOver(1);

					// 抢库存
					String str = promotionRedisDB.headPop(stockKey);
					if (StringUtils.isEmpty(str)) {
						result.setCode(Constants.PROMOTION_NO_STOCK);
						result.setErrorMessage("该商品已经售罄");
						result.setResult(openedId);
						return result;
					}
				}
				// 更新发起砍价表
				buyerLaunchBargainInfoDAO.updateBuyerLaunchBargainInfo(buyerBargainLaunchReqDTO);
				// 队列长度为0或者队列为不存在的时候则说明已经砍完
				if (!promotionRedisDB.exists(key) && !promotionRedisDB.exists(stockKey)) {
					PromotionBargainInfoResDTO redisDTO = new PromotionBargainInfoResDTO();
					redisDTO.setPromotionId(promotionId);
					List<PromotionBargainInfoResDTO> listPromotions = promotionBargainRedisHandle
							.getRedisBargainInfoList(redisDTO);
					if (listPromotions != null && listPromotions.size() > 0) {
						for (PromotionBargainInfoResDTO p : listPromotions) {
							if (levelCode.equals(p.getLevelCode())) {
								p.setIsBargainOver(2);
								break;
							}
						}
						promotionBargainRedisHandle.addBargainInfo3Redis(listPromotions);
					}
				}

				// 插入砍价记录
				BuyerBargainRecordReqDTO buyerBargainRecord = new BuyerBargainRecordReqDTO();
				buyerBargainRecord.setBargainCode(bargainCode);
				buyerBargainRecord.setBargainPersonCode(openedId);
				buyerBargainRecord.setBargainAmount(new BigDecimal(bargainPrice));
				buyerBargainRecord.setBargainPresonName(helperName);
				buyerBargainRecord.setBargainTime(new Date());
				buyerBargainRecord.setCreateId(1);
				buyerBargainRecord.setCreateName(helperName);
				buyerBargainRecord.setCreateTime(new Date());
				buyerBargainRecord.setHeadSculptureUrl(helperPicture);
				buyerBargainRecord.setMessageId(messageId);
				buyerBargainRecordDAO.insertBuyerBargainRecord(buyerBargainRecord);
				// 为判断是否砍过价做准备
				String str1 = "01";
				promotionRedisDB.setHash(Constants.IS_BUYER_BARGAIN + promotionId,
						openedId + promotionId + levelCode + bargainCode, str1);
				result.setCode(ResultCodeEnum.SUCCESS.getCode());
				result.setResultMessage("厉害了，砍价成功");
				result.setResult(openedId);
				return result;
				// redis砍价商品不存在
			} else {
				result.setCode(ResultCodeEnum.PROMOTION_NOT_EXIST.getCode());
				result.setErrorMessage("该砍价活动不存在");
				result.setResult(openedId);
				return result;
			}
		} catch (PromotionCenterBusinessException pbe) {
			result.setCode(pbe.getCode());
			result.setErrorMessage(pbe.getMessage());
		} catch (Exception e) {
			result.setCode(ResultCodeEnum.ERROR.getCode());
			result.setErrorMessage(ExceptionUtils.getStackTraceAsString(e));
		}
		return result;
	}
	
}
