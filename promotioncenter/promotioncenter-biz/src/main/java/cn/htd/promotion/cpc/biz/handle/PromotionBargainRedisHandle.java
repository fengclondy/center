package cn.htd.promotion.cpc.biz.handle;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.htd.common.constant.DictionaryConst;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.promotion.cpc.common.constants.PromotionCenterCodeConst;
import cn.htd.promotion.cpc.common.constants.RedisConst;
import cn.htd.promotion.cpc.common.emums.ResultCodeEnum;
import cn.htd.promotion.cpc.common.exception.PromotionCenterBusinessException;
import cn.htd.promotion.cpc.common.util.BargainPriceSplit;
import cn.htd.promotion.cpc.common.util.PromotionCenterRedisDB;
import cn.htd.promotion.cpc.dto.response.BuyerLaunchBargainInfoResDTO;
import cn.htd.promotion.cpc.dto.response.BuyerUseBargainLogDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBargainInfoResDTO;
import cn.htd.promotion.cpc.dto.response.PromotionInfoDTO;

import com.alibaba.fastjson.JSON;

@Service("promotionBargainRedisHandle")
public class PromotionBargainRedisHandle {

	protected static transient Logger logger = LoggerFactory
			.getLogger(PromotionBargainRedisHandle.class);

	@Resource
	private DictionaryUtils dictionary;

	@Resource
	private PromotionCenterRedisDB promotionRedisDB;

	/**
	 * 保存砍价活动的启用状态
	 *
	 * @param promotionInfo
	 */
	public void saveBargainValidStatus2Redis(PromotionInfoDTO promotionInfo) {
		promotionRedisDB.setHash(RedisConst.REDIS_BARGAIN_VALID,
				promotionInfo.getPromotionId(), promotionInfo.getShowStatus());
	}

	/**
	 * 取得Redis砍价活动信息
	 *
	 * @param promotionId
	 */
	public List<PromotionBargainInfoResDTO> getRedisBargainInfoList(
			String promotionId) throws PromotionCenterBusinessException {
		List<PromotionBargainInfoResDTO> promotionBargainInfoList = null;
		String promotionBargainInfoJsonStr = "";
		String validStatus = "";
		validStatus = promotionRedisDB.getHash(RedisConst.REDIS_BARGAIN_VALID,
				promotionId);
		if (!StringUtils.isEmpty(validStatus)
				&& !dictionary.getValueByCode(
						DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
						DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID)
						.equals(validStatus)) {
			throw new PromotionCenterBusinessException(
					PromotionCenterCodeConst.BARGAIN_NOT_VALID, "砍价活动ID:"
							+ promotionId + " 该砍价活动未启用");
		}
		promotionBargainInfoJsonStr = promotionRedisDB.getHash(
				RedisConst.REDIS_BARGAIN, promotionId);
		promotionBargainInfoList = JSON.parseArray(promotionBargainInfoJsonStr,
				PromotionBargainInfoResDTO.class);
		if (promotionBargainInfoList == null) {
			throw new PromotionCenterBusinessException(
					PromotionCenterCodeConst.PROMOTION_NOT_EXIST, "砍价活动ID:"
							+ promotionId + " 该砍价活动不存在!");
		}
		return promotionBargainInfoList;
	}

	/**
	 * 删除Redis砍价活动信息
	 *
	 * @param promotionId
	 */
	public void deleteRedisBargainInfo(String promotionId) {
		List<PromotionBargainInfoResDTO> promotionBargainInfoList = null;
		String promotionBargainInfoJsonStr = "";
		promotionBargainInfoJsonStr = promotionRedisDB.getHash(
				RedisConst.REDIS_BARGAIN, promotionId);
		promotionBargainInfoList = JSON.parseArray(promotionBargainInfoJsonStr,
				PromotionBargainInfoResDTO.class);
		if (promotionBargainInfoList != null) {
			promotionRedisDB.delHash(RedisConst.REDIS_BARGAIN, promotionId);
			promotionRedisDB.delHash(RedisConst.REDIS_BARGAIN_VALID,
					promotionId);
		}
	}

	/**
	 * 保存砍价活动信息进Redis
	 *
	 * @param BargainInfo
	 */
	public void addBargainInfo2Redis(
			List<PromotionBargainInfoResDTO> promotionBargainInfoList) {
		String promotionId = "";
		String promotionSlogan = "";
		List<String> sloganList = null;
		if (null != promotionBargainInfoList
				&& !promotionBargainInfoList.isEmpty()) {
			promotionId = promotionBargainInfoList.get(0).getPromotionId();
			promotionSlogan = promotionBargainInfoList.get(0).getPromotionSlogan();
			if(StringUtils.isNotEmpty(promotionSlogan)){
				sloganList = JSON.parseArray(promotionSlogan, String.class);
			}
			for (PromotionBargainInfoResDTO dto : promotionBargainInfoList) {
				dto.setCreateTime(new Date());
				dto.setModifyTime(new Date());
				dto.setSloganList(sloganList);
				dto.setShowStatus(dictionary.getValueByCode(
						DictionaryConst.TYPE_PROMOTION_VERIFY_STATUS,
						DictionaryConst.OPT_PROMOTION_VERIFY_STATUS_VALID));
			}
			promotionRedisDB.setHash(RedisConst.REDIS_BARGAIN, promotionId,
					JSON.toJSONString(promotionBargainInfoList));
			saveBargainValidStatus2Redis(promotionBargainInfoList.get(0));
		}
	}

	/**
	 * 更新砍价变化活动状态
	 *
	 * @param promotionInfo
	 */
	public void updateRedisTimeilimitedStatus(
			List<PromotionBargainInfoResDTO> promotionBargainInfoList) {
		if (null != promotionBargainInfoList
				& !promotionBargainInfoList.isEmpty()) {
			String promotionId = promotionBargainInfoList.get(0)
					.getPromotionId();
			promotionRedisDB.setHash(RedisConst.REDIS_BARGAIN, promotionId,
					JSON.toJSONString(promotionBargainInfoList));
			saveBargainValidStatus2Redis(promotionBargainInfoList.get(0));
		}
	}

	/**
	 * 校验会员是否已参加过砍价活动
	 *
	 * @param promotionId
	 * @param buyerCode
	 * @return
	 */
	public boolean checkBuyerBargainCondition(String promotionId,
			String buyerCode, String levelCode) {
		BuyerUseBargainLogDTO useLog = null;
		String useLogRedisKey = buyerCode + "&" + promotionId + "&" + levelCode;
		String useLogJsonStr = promotionRedisDB.getHash(
				RedisConst.REDIS_BUYER_BARGAIN_USELOG, useLogRedisKey);
		useLog = JSON.parseObject(useLogJsonStr, BuyerUseBargainLogDTO.class);
		if (useLog == null) {
			return true;
		}
		if (StringUtils.isEmpty(useLog.getPromotionId())) {
			return true;
		}
		return false;
	}

	/**
	 * 更新Redis中的砍价活动参加记录并更新DB
	 *
	 * @param useLogList
	 */
	public void updateRedisUseBargainLog(BuyerUseBargainLogDTO useLog) {
		String useLogRedisKey = "";
		if (useLog == null) {
			return;
		}
		useLogRedisKey = useLog.getBuyerCode() + "&" + useLog.getPromotionId()
				+ "&" + useLog.getLevelCode();
		promotionRedisDB.setHash(RedisConst.REDIS_BUYER_BARGAIN_USELOG,
				useLogRedisKey, JSON.toJSONString(useLog));
	}

	/**
	 * 添加砍价发起流程金额拆分
	 * 
	 * @throws Exception
	 */
	public BigDecimal addRedisBargainPriceSplit(BuyerLaunchBargainInfoResDTO dto)
			throws Exception {
		BigDecimal popPrice = null;
		int price = (dto.getGoodsCostPrice().subtract(dto.getGoodsFloorPrice()).multiply(new BigDecimal("100"))).intValue();
		BargainPriceSplit priceSplit = new BargainPriceSplit();
		String key = "";
		List<String> priceList = priceSplit.splitRedPackets(price,
				dto.getPartakeTimes());
		if (null != priceList && !priceList.isEmpty()) {
			key = RedisConst.REDIS_BARGAIN_PRICE_SPLIT + "_" + dto.getPromotionId() + "_" + dto.getLevelCode() + "_"
					+ dto.getBargainCode();
			for (String str : priceList) {
				promotionRedisDB.headPush(key, str);
			}
			String priceStr = promotionRedisDB.tailPop(key);
			popPrice = new BigDecimal(priceStr);
		}
		return popPrice;
	}
}
