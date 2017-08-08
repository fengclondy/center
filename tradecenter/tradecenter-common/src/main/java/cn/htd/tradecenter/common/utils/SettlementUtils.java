package cn.htd.tradecenter.common.utils;


import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import cn.htd.common.dao.util.RedisDB;
import cn.htd.common.util.DateUtils;


/**
 * @Title: SettlementUtils.java
 * @Description: 结算单工具类
 * @date 2017年2月18日 下午11:43:02
 * @version V1.0
 **/
@Component("settlementUtils")
public class SettlementUtils {
	
	
	// Redis结算单号数据
	private static final String REDIS_SETTLEMENT_KEY = "B2B_MIDDLE_SETTLEMENT_OSB_SEQ";
	private static final String REDIS_WITHDRAWALS_KEY = "B2B_MIDDLE_WITHDRAWALS_OSB_SEQ";

	private static final String DEFAULT_CODE = "10";
	public static final String B2B_MALL_CART_SHOP_HASH = "B2B_MALL_CART_SHOP_HASH";
	public static final String DJ = "DJ";


	@Resource
	private RedisDB redisDB;


	/**
	 * 获得运行状态 1、正在执行 2、执行完成
	 * @param key
	 * @param field
	 * @return
	 */
	public String getTaskRunFlag(String key, String field){
		return redisDB.getHash(key, field);
	}
	
	/**
	 * 设置运行状态 1、正在执行 2、执行完成
	 * @param key
	 * @param field
	 * @param value
	 */
	public void setTaskRunFlag(String key, String field, String value){
		redisDB.setHash(key, field, value);
	}
	
	/**
	 * 设置运行的次数
	 * @param key
	 * @param field
	 * @param value
	 */
	public void setTaskRunCount(String key, String value){
		redisDB.set(key, value);
	}
	
	/**
	 * 获取运行的次数
	 * @param key
	 */
	public String getTaskRunCount(String key){
		return redisDB.get(key);
	}
	
	/**
	 * 设置失效时间
	 * @param key
	 * @param seconds
	 */
	public void setTaskRunCountExpire(String key, int seconds) {
		redisDB.expire(key, seconds);
	}
	
	public String getMerchOrderNoForERP(String field) {
		return redisDB.getHash(B2B_MALL_CART_SHOP_HASH , DJ + field);
	}
	
	public void dropMerchOrderNoForERP(String field) {
		redisDB.delHash(B2B_MALL_CART_SHOP_HASH, DJ + field);
	}
	
	/**
	 * 结算单号生成方法
	 * 
	 * @param platCode 外部供应商 10 平台公司 20
	 * @return
	 */
	public String generateSettlementNo(String platCode) {
		String sub3 = getCacheSeq(REDIS_SETTLEMENT_KEY, 10000L);
		String dateString = DateUtils.getCurrentDate("yyMMdd");
		StringBuilder stringBuilder = new StringBuilder(DEFAULT_CODE);
		if (StringUtils.isNotEmpty(platCode)) {
			stringBuilder = new StringBuilder(platCode);
		}
		stringBuilder.append(dateString);
		stringBuilder.append(sub3);
		String settlementNo = stringBuilder.toString();
		return settlementNo;
	}
	
	private String getCacheSeq(String seqKey, Long maxValue) {
		Long seqIndexLong = redisDB.incr(seqKey);
		if (seqIndexLong >= maxValue) {
			redisDB.set(seqKey, "1");
			seqIndexLong = 1L;
		}
		int maxStrLength = maxValue.toString().length() - 1;
		String zeroString = String.format("%0" + maxStrLength + "d", seqIndexLong);
		return zeroString;
	}

	public String generateWithdrawalsNo(String platCode) {
		String sub3 = getCacheSeq(REDIS_WITHDRAWALS_KEY, 1000000L);
		String dateString = DateUtils.getCurrentDate("yyMMdd");
		StringBuilder stringBuilder = new StringBuilder(DEFAULT_CODE);
		if (StringUtils.isNotEmpty(platCode)) {
			stringBuilder = new StringBuilder(platCode);
		}
		stringBuilder.append(dateString);
		stringBuilder.append(sub3);
		return stringBuilder.toString();
	}

}
