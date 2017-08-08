package cn.htd.tradecenter.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import cn.htd.common.dao.util.RedisDB;
import cn.htd.common.util.DateUtils;

/**
 * @Title: GeneratorUtils.java
 * @Description: 生成各类业务ID
 * @date 2015年9月1日 下午11:43:02
 * @version V1.0
 **/
@Component("generatorUtils")
public class GeneratorUtils {
	// Redis订单号数据
	private static final String REDIS_ORDER_KEY = "B2B_MIDDLE_ORDER_OSB_SEQ";
	// Redis订单行号数据
	private static final String REDIS_ORDER_ITEM_KEY = "B2B_MIDDLE_SUBORDER_OSB_SEQ";
	// Redis交易号数据
	private static final String REDIS_TRADE_KEY = "B2B_MIDDLE_CART_TSB_SEQ";
	// RedisMessageId数据
	private static final String REDIS_MESSAGE_ID_KEY = "B2B_MIDDLE_MESSAGEID_MSB_SEQ";
	// Redis结算单号数据
	private static final String REDIS_SETTLEMENT_KEY = "B2B_MIDDLE_SETTLEMENT_OSB_SEQ";

	private static Map<String, String> map = new HashMap<String, String>();

	private static final String DEFAULT_CODE = "10";

	@Resource
	private RedisDB redisDB;

	/**
	 * 带自定义前缀的ID生成
	 * 
	 * @param prefixKey 自定义的0-7位前缀
	 * @param ip
	 * @return
	 */
	public String customGenerateId(String prefixKey, String ip) {
		return prefixKey + generateMessageId(ip);
	}

	/**
	 * 带自定义前缀的流水ID生成
	 * 
	 * @param prefixKey 自定义的0-3位前缀
	 * @param ip
	 * @return
	 */
	public String generateChannelId(String prefixKey) {
		if (StringUtils.isBlank(prefixKey)) {
			prefixKey = "HTD-";
		}
		return prefixKey + generateChannelId();
	}

	/**
	 * ID生成 23位，13位时间戳+6位IP4后两段左补0+4位循环（0001-9999）左补0
	 * 
	 * @param ip
	 * @return
	 */
	public String generateMessageId(String ip) {

		String id = null;
		try {
			id = String.valueOf(System.currentTimeMillis());
			ip = getHostIp();
			String[] sub2 = ip.split("\\.");
			String sub3 = getCacheSeq(REDIS_MESSAGE_ID_KEY, 10000L);
			id = id + String.format("%03d", Integer.valueOf(sub2[2])) + String.format("%03d", Integer.valueOf(sub2[3]))
					+ sub3;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * 订单号生成方法
	 * 
	 * @param platCode 平台编码 商城 10 汇掌柜 20
	 * @return
	 */
	public String generateOrderNo(String platCode) {
		String sub3 = getCacheSeq(REDIS_ORDER_KEY, 100000L);
		return buildGenricIdMethod(platCode, sub3);
	}

	/**
	 * 结算单号生成方法
	 * 
	 * @param platCode 外部供应商 10 平台公司 20
	 * @return
	 */
	public String generateSettlementNo(String platCode) {
		String sub3 = getCacheSeq(REDIS_SETTLEMENT_KEY, 10000L);
		String dateString = DateUtils.getCurrentDate("yyyyMMdd");
		StringBuilder stringBuilder = new StringBuilder(DEFAULT_CODE);
		if (StringUtils.isNotEmpty(platCode)) {
			stringBuilder = new StringBuilder(platCode);
		}
		stringBuilder.append(dateString);
		stringBuilder.append(sub3);
		String settlementNo = stringBuilder.toString();
		return settlementNo;
	}

	/**
	 * 交易号生成方法
	 * 
	 * @param platCode 平台编码 商城 10 汇掌柜 20
	 * @return
	 */
	public String generateTradeNo(String platCode) {
		String sub3 = getCacheSeq(REDIS_TRADE_KEY, 10000L);
		return buildGenricIdMethod(platCode, sub3);
	}

	/**
	 * 两位循环序列生成器（用于订单行号序列生成）
	 * 
	 * @param orderNo
	 * @return
	 */
	public String generateOrderItemNo(String orderNo) {
		String subNo = getCacheSeq(REDIS_ORDER_ITEM_KEY, 100L);
		StringBuffer stringBuilder = new StringBuffer();
		stringBuilder.append(orderNo);
		stringBuilder.append(subNo);
		return stringBuilder.toString();
	}

	/**
	 * 组装唯一性序列数据的统一方法
	 * 
	 * @param platCode 平台编码 商城 10 汇掌柜 20，sub3是序列
	 * @return
	 */
	private String buildGenricIdMethod(String platCode, String sub3) {
		String dateString = DateUtils.getCurrentDate("yyMMddHHmmss");
		StringBuilder stringBuilder = new StringBuilder(DEFAULT_CODE);
		if (StringUtils.isNotEmpty(platCode)) {
			stringBuilder = new StringBuilder(platCode);
		}
		stringBuilder.append(dateString);
		stringBuilder.append(sub3);
		String orderNo = stringBuilder.toString();
		return orderNo;
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
		Long seqIndexLong = redisDB.incr(seqKey);
		if (seqIndexLong >= maxValue) {
			redisDB.set(seqKey, "1");
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
		Long dateString = 100000000000L;
		System.out.println(String.format("%0" + dateString.toString().length() + "d", 120));

	}
}
