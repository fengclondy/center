package cn.htd.promotion.cpc.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import cn.htd.common.util.DateUtils;

/**
 * @Title: GeneratorUtils.java
 * @Description: 生成各类业务ID
 * @date 2015年9月1日 下午11:43:02
 * @version V1.0
 **/
@Component("generatorUtils")
public class GeneratorUtils {
	// Redis促销活动编码数据
	private static final String REDIS_PROMOTION_ID_KEY = "B2B_MIDDLE_PROMOTIONID_PSB_SEQ";
	// Redis促销活动层级数据
	private static final String REDIS_PROMOTION_LEVEL_KEY = "B2B_MIDDLE_PROMOTION_LEVEL_PSB_SEQ";
	// Redis优惠券编号数据
	private static final String REDIS_COUPON_CODE_KEY = "B2B_MIDDLE_COUPONCODE_PSB_SEQ";
	// Redis促销砍价活动编码数据
	private static final String REDIS_BARGAIN_ID_KEY = "B2B_MIDDLE_BARGAIN_PSB_SEQ";
	// Redis促销砍价活动发起数据
	private static final String REDIS_BARGAIN_LAUNCH_KEY = "B2B_MIDDLE_BARGAIN_LAUNCH_PSB_SEQ";

	private static Map<String, String> map = new HashMap<String, String>();

	@Resource
	private PromotionCenterRedisDB marketRedisDB;

	/**
	 * 促销活动编码生成方法
	 * 
	 * @param platCode
	 *            促销活动类型 1:优惠券，2:秒杀，3:扭蛋，4:砍价，5:总部秒杀
	 * @return
	 */
	public String generatePromotionId(String platCode) {
		String promotionId = getCacheSeq(REDIS_PROMOTION_ID_KEY, 10000L);
		String yy = DateUtils.getCurrentDate("yyHHmmss");
		if (StringUtils.isEmpty(platCode)) {
			platCode = "0";
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(platCode);
		stringBuilder.append(yy);
		stringBuilder.append(promotionId);
		return stringBuilder.toString();
	}
	
	/**
	 * 促销砍价活动编码生成方法
	 * 
	 * @param platCode
	 *            促销活动类型 1:优惠券，2:秒杀，3:扭蛋，4:砍价，5:总部秒杀
	 * @return
	 */
	public String generatePromotionGargainId(String platCode) {
		String promotionId = getCacheSeq(REDIS_BARGAIN_ID_KEY, 10000L);
		String yy = DateUtils.getCurrentDate("yyHHmmss");
		if (StringUtils.isEmpty(platCode)) {
			platCode = "0";
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(platCode);
		stringBuilder.append(yy);
		stringBuilder.append(promotionId);
		return stringBuilder.toString();
	}
	
	/**
	 * 促销活动砍价发起信息编码生成方法
	 * @param platCode
	 * @return
	 */
	public String generatePromotionGargainLaunchCode(String platCode) {
		String promotionId = getCacheSeq(REDIS_BARGAIN_LAUNCH_KEY, 10000L);
		String yy = DateUtils.getCurrentDate("yyHHmmss");
		if (StringUtils.isEmpty(platCode)) {
			platCode = "0";
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(platCode);
		stringBuilder.append(yy);
		stringBuilder.append(promotionId);
		return stringBuilder.toString();
	}
	
	/**
	 * 促销活动层级编码生成方法
	 * 
	 * @param promotionId
	 * @return
	 */
	public String generatePromotionLevelCode(String promotionId) {
		String levelCode = getCacheSeq(REDIS_PROMOTION_LEVEL_KEY, 100L);
		if (StringUtils.isEmpty(promotionId)) {
			return "";
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(promotionId);
		stringBuilder.append(levelCode);
		return stringBuilder.toString();
	}

	/**
	 * 优惠券编码生成方法
	 * 
	 * @param platCode
	 *            优惠券类型 1：满减券，2:折扣券
	 * @return
	 */
	public String generateCouponCode(String platCode) {
		String couponCode = getCacheSeq(REDIS_COUPON_CODE_KEY, 100000000000L);
		if (StringUtils.isEmpty(platCode)) {
			platCode = "0";
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(platCode);
		stringBuilder.append(couponCode);
		return stringBuilder.toString();
	}

	/**
	 * 支付渠道流水生成 17位，13位时间戳+4位循环（0001-9999）左补0
	 * 
	 * @param ip
	 * @return
	 */
	public synchronized String generateChannelId() {

		String id = String.valueOf(System.currentTimeMillis());

		if (null == map.get("pckey")) {// 初始化
			map.put("pckey", "1");
		} else if ("10000".equals(map.get("pckey"))) {// 复位
			map.put("pckey", "1");
		}
		String sub3 = String.format("%04d", Integer.valueOf(map.get("pckey")));
		id = id + sub3;
		map.put("pckey", String.valueOf(Integer.valueOf(sub3) + 1));
		return id;

	}

	private String getCacheSeq(String seqKey, Long maxValue) {
		Long seqIndexLong = marketRedisDB.incr(seqKey);
		if (seqIndexLong >= maxValue) {
			marketRedisDB.set(seqKey, "1");
			seqIndexLong = 1L;
		}
		int maxStrLength = maxValue.toString().length() - 1;
		String zeroString = String.format("%0" + maxStrLength + "d", seqIndexLong);
		return zeroString;
	}

	/*
	 * 获取服务器ip
	 */
	public String getHostIp() throws UnknownHostException {
		String ip = InetAddress.getLocalHost().getHostAddress();
		return ip;
	}

	public static void main(String[] args) throws InterruptedException {

		// System.out.println(GenerateIdsUtil.generateId("199.168.3.76"));
		// System.out.println(GenerateIdsUtil.generateId("199.168.3.76"));
		// System.out.println(GenerateIdsUtil.customGenerateId("test","192.168.110.6"));
		// System.out.println(GenerateIdsUtil.generateOrgCode("1","99"));
		Long dateString = 9998888L;
		System.out.println(DateUtils.getCurrentDate("yyHHmmss"));

	}
}
