package cn.htd.promotion.cpc.biz.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.biz.dao.BuyerBargainRecordDAO;
import cn.htd.promotion.cpc.biz.dao.BuyerLaunchBargainInfoDAO;
import cn.htd.promotion.cpc.biz.dao.PromotionInfoDAO;
import cn.htd.promotion.cpc.biz.dmo.BuyerLaunchBargainInfoDMO;
import cn.htd.promotion.cpc.biz.handle.PromotionBargainRedisHandle;
import cn.htd.promotion.cpc.biz.service.BuyerLaunchBargainInfoService;
import cn.htd.promotion.cpc.common.constants.PromotionCenterCodeConst;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.ExecuteResult;
import cn.htd.promotion.cpc.common.util.GeneratorUtils;
import cn.htd.promotion.cpc.common.util.StringUtilHelper;
import cn.htd.promotion.cpc.common.util.ValidateResult;
import cn.htd.promotion.cpc.common.util.ValidationUtils;
import cn.htd.promotion.cpc.dto.request.BuyerBargainRecordReqDTO;
import cn.htd.promotion.cpc.dto.request.BuyerBargainLaunchReqDTO;
import cn.htd.promotion.cpc.dto.response.BuyerLaunchBargainInfoResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionInfoDTO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service("buyerLaunchBargainInfoService")
public class BuyerLaunchBargainInfoServiceImpl implements BuyerLaunchBargainInfoService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BuyerLaunchBargainInfoServiceImpl.class);
	
	@Resource
	private BuyerLaunchBargainInfoDAO buyerLaunchBargainInfoDAO;
	
    @Resource
    private PromotionInfoDAO promotionInfoDAO;
    
    @Resource
    private DictionaryUtils dictionary;
    
	@Resource
	private GeneratorUtils noGenerator;
	
	@Resource
	private PromotionBargainRedisHandle promotionBargainRedisHandle;
	
	@Resource
	private BuyerBargainRecordDAO buyerBargainRecordDAO;
	
	@Override
	public List<BuyerLaunchBargainInfoResDTO> getBuyerLaunchBargainInfoByBuyerCode(String buyerCode,String messageId) {
		LOGGER.info("MessageId{}:调用buyerLaunchBargainInfoDAO.getBuyerLaunchBargainInfoByBuyerCode（）方法开始,入参{}",messageId,buyerCode+":"+messageId);
		List<BuyerLaunchBargainInfoDMO> buyerBargainInfoList = buyerLaunchBargainInfoDAO.getBuyerLaunchBargainInfoByBuyerCode(buyerCode);
		LOGGER.info("MessageId{}:调用buyerLaunchBargainInfoDAO.getBuyerLaunchBargainInfoByBuyerCode（）方法开始,出参{}",messageId,
				JSON.toJSONString(buyerBargainInfoList));
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
		List<BuyerLaunchBargainInfoDMO> buyerBargainInfoList = buyerLaunchBargainInfoDAO.getBuyerLaunchBargainInfoByPromotionId(promotionId);
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
		LOGGER.info("MessageId{}:调用buyerLaunchBargainInfoDAO.addBuyerBargainLaunch（）方法开始,入参{}",messageId,StringUtilHelper.getClassParam(bargainInfoDTO)+":"+messageId);
		ExecuteResult<BuyerLaunchBargainInfoResDTO> result = new ExecuteResult<BuyerLaunchBargainInfoResDTO>();
		try {
			// 输入DTO的验证
			ValidateResult validateResult = ValidationUtils.validateEntity(bargainInfoDTO);
			if (validateResult.isHasErrors()) {
				throw new PromotionCenterBusinessException(PromotionCenterCodeConst.PARAMETER_ERROR,
	                  validateResult.getErrorMsg());
			}
			PromotionInfoDTO promotionInfo = promotionInfoDAO.queryById(bargainInfoDTO.getPromotionId());
			if (promotionInfo == null) {
                throw new PromotionCenterBusinessException(PromotionCenterCodeConst.PROMOTION_NOT_EXIST, "砍价活动不存在");
            }
			String promotionType = promotionInfo.getShowStatus();
			if(dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
                    DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID).equals(promotionType)){
				throw new PromotionCenterBusinessException(PromotionCenterCodeConst.BARGAIN_NOT_VALID,
		                  "砍价活动未启用");
			}
			if((new Date()).before(promotionInfo.getEffectiveTime())){
				throw new PromotionCenterBusinessException(PromotionCenterCodeConst.BARGAIN_NOT_VALID,
		                  "砍价活动时间未开始");
			}
			if((new Date()).before(promotionInfo.getInvalidTime())){
				throw new PromotionCenterBusinessException(PromotionCenterCodeConst.BARGAIN_NOT_VALID,
		                  "砍价活动时间已结束");
			}
			Integer launchTimes = buyerLaunchBargainInfoDAO.queryBuyerLaunchBargainInfoNumber(bargainInfoDTO);
			if(null != launchTimes && launchTimes.intValue() >= promotionInfo.getDailyBuyerPartakeTimes()){
				throw new PromotionCenterBusinessException(PromotionCenterCodeConst.PROMOTION_BARGAIN_JOIN_QTY,
		                  "该砍价活动商品参与次数已上限");
			}
			bargainInfoDTO.setIsBargainOver(1);
			Integer bargainLockingStockNumber = buyerLaunchBargainInfoDAO.queryBuyerLaunchBargainInfoNumber(bargainInfoDTO);
			if(null != bargainLockingStockNumber && bargainLockingStockNumber.intValue() >= bargainInfoDTO.getGoodsNum()){
				throw new PromotionCenterBusinessException(PromotionCenterCodeConst.PROMOTION_NO_STOCK,
		                  "砍价商品库存不足");
			}
			bargainInfoDTO.setIsBargainOver(0);
			bargainInfoDTO.setBargainCode(noGenerator.generatePromotionGargainLaunchCode(promotionType));
			BigDecimal popPrice = promotionBargainRedisHandle.addRedisBargainPriceSplit(bargainInfoDTO);
			if(null == popPrice){
				throw new PromotionCenterBusinessException(PromotionCenterCodeConst.POMOTION_SPLIT_PRICE_ERROR,
		                  "拆分金额失败");
			}
			BigDecimal goodsCurrentPrice = (bargainInfoDTO.getGoodsCostPrice().subtract(bargainInfoDTO.getGoodsFloorPrice()))
					.subtract(popPrice);
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
			buyerBargainRecordDAO.insertBuyerBargainRecord(buyerBargainRecord);
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

}
