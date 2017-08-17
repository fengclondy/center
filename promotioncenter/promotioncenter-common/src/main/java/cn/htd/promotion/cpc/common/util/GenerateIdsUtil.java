package cn.htd.promotion.cpc.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Package: com.thfund.common.utils
 * @Title: GenerateIdsUtil.java
 * @Description: 生成各类业务ID
 * @date 2015年9月1日 下午11:43:02
 * @version V1.0
 **/
public class GenerateIdsUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(GenerateIdsUtil.class);

	private static Map<String, String> map = new HashMap<String, String>();

	private static final String SEQ_ORDER_KEY = "B2B_MIDDLE_ORDER_OSB_SEQ";

	private static final String SEQ_SUB_ORDER_KEY = "B2B_MIDDLE_SUBORDER_OSB_SEQ";

	private static final String SEQ_CART_KEY = "B2B_MIDDLE_CART_TSB_SEQ";

	private static final String SEQ_MESSAGEID_KEY = "B2B_MIDDLE_MESSAGEID_MSB_SEQ";

	private static final String DEFAULT_CODE = "10";

	private static Map<Integer, String> STRING_LENGTH_MAP = new HashMap<Integer, String>();

	static {
		STRING_LENGTH_MAP.put(1, "");
		STRING_LENGTH_MAP.put(2, "0");
		STRING_LENGTH_MAP.put(3, "00");
		STRING_LENGTH_MAP.put(4, "000");
		STRING_LENGTH_MAP.put(5, "0000");
	}

	public static GenerateIdsUtil getInstance() {
		GenerateIdsUtil generateIdsUtil = new GenerateIdsUtil();
		return generateIdsUtil;
	}

	/**
	 * 带自定义前缀的ID生成
	 * 
	 * @param prefixKey
	 *            自定义的0-7位前缀
	 * @param ip
	 * @return
	 */
	public static String customGenerateId(String prefixKey, String ip) {
		return prefixKey + generateId(ip);
	}

	/**
	 * 带自定义前缀的流水ID生成
	 * 
	 * @param prefixKey
	 *            自定义的0-3位前缀
	 * @param ip
	 * @return
	 */
	public static String generateChannelId(String prefixKey) {
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
	public static String generateId(String ip) {

		String id = null;
		try {
			id = String.valueOf(System.currentTimeMillis());
			ip = getHostIp();
			String[] sub2 = ip.split("\\.");
			String sub3 = getInstance().getCacheSeq(SEQ_MESSAGEID_KEY, 10000L, 1);

			id = id + String.format("%03d", Integer.valueOf(sub2[2])) + String.format("%03d", Integer.valueOf(sub2[3]))
					+ sub3;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * 订单号生成方法
	 * 
	 * @param platCode
	 *            平台编码 商城 10 汇掌柜 20 秒杀预占编码 50
	 * @return
	 */
	public static String generateOrderID(String platCode) {
		String sub3 = getInstance().getCacheSeq(SEQ_ORDER_KEY, 100000L, 1);
		return buildGenricIdMethod(platCode, sub3);
	}

	/**
	 * 交易号生成方法
	 * 
	 * @param platCode
	 *            平台编码 商城 10 汇掌柜 20
	 * @return
	 */
	public static String generateTradeID(String platCode) {
		String sub3 = getInstance().getCacheSeq(SEQ_CART_KEY, 10000L, 1);
		return buildGenricIdMethod(platCode, sub3);
	}

	/**
	 * 两位循环序列生成器（用于订单行号序列生成）
	 * 
	 * @return
	 */
	public static String generateSubOrderSeq() {
		return getInstance().getCacheSeq(SEQ_SUB_ORDER_KEY, 100L, 1);
	}

	/**
	 * 两位循环序列生成器（用于订单行号序列生成）
	 * 
	 * @return
	 */
	public static List<Integer> generateSubOrderSeqList(int subOrderCount) {
		int seqOrderSeq = 50;
		List<Integer> subOrderSeqlist = new ArrayList<Integer>();
		for (int i = 0; i < subOrderCount; i++) {
			int seqOrderSeqFinal = seqOrderSeq - subOrderCount + i + 1;
			subOrderSeqlist.add(seqOrderSeqFinal);
		}
		return subOrderSeqlist;
	}

	/**
	 * 组装唯一性序列数据的统一方法
	 * 
	 * @param platCode
	 *            平台编码 商城 10 汇掌柜 20，sub3是序列
	 * @return
	 */
	private static String buildGenricIdMethod(String platCode, String sub3) {
		String dateString = DateUtil.getDaySS().substring(1, DateUtil.getDaySS().length());
		StringBuilder stringBuilder = new StringBuilder(DEFAULT_CODE);
		if (StringUtils.isNotEmpty(platCode)) {
			stringBuilder = new StringBuilder(platCode);
		}
		stringBuilder.append(dateString);
		stringBuilder.append(sub3);
		String orderId = stringBuilder.toString();
		return orderId;
	}

	/**
	 * 支付渠道流水生成 17位，13位时间戳+4位循环（0001-9999）左补0
	 * 
	 * @param ip
	 * @return
	 */
	public static synchronized String generateChannelId() {

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

	private String getCacheSeq(String seqKey, Long maxValue, int seqIncrBy) {
		RedisUtil redisUtil = RedisUtil.getInstance();
		Long seqIndexLong = redisUtil.incrBy(seqKey, seqIncrBy);
		if (seqIndexLong >= maxValue) {
			int seqIndexLength = String.valueOf(seqIndexLong).length();
			String seqPre = String.valueOf(seqIndexLong).substring(0,
					seqIndexLength - String.valueOf(maxValue).length() + 1);
			seqIndexLong = seqIndexLong - Long.valueOf(seqPre) * maxValue;
		}
		String zeroString = STRING_LENGTH_MAP.get(maxValue.toString().length() - seqIndexLong.toString().length());
		StringBuilder stringBuilder = new StringBuilder(zeroString);
		stringBuilder.append(seqIndexLong);
		return stringBuilder.toString();
	}

	/*
	 * 获取服务器ip
	 */
	public static String getHostIp() {
		String ip = "";
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			StringWriter w = new StringWriter();
			e.printStackTrace(new PrintWriter(w));
			LOGGER.error("获取ip发生异常-异常信息为：" + w.toString());
		}
		return ip;
	}

	public static void main(String[] args) throws InterruptedException {

		generateSubOrderSeqList(5);
		// System.out.println(GenerateIdsUtil.generateId("199.168.3.76"));
		// System.out.println(GenerateIdsUtil.generateId("199.168.3.76"));
		// System.out.println(GenerateIdsUtil.customGenerateId("test","192.168.110.6"));
		// System.out.println(GenerateIdsUtil.generateOrgCode("1","99"));
		// String dateString = "20171010101211";
		// System.out.println(dateString.substring(1,dateString.length()));
		/*
		 * int seqIndexLength = String.valueOf(110001).length(); String seqPre =
		 * String.valueOf(110001).substring(0,seqIndexLength-String.valueOf(
		 * 10000).length()+1); System.out.println(seqPre);
		 */
		// TestSubOrder testSubOrder = new TestSubOrder();
		// testSubOrder.setPriceAmount("100");
		// testSubOrder.setProductCode("100032");
		// TestSubOrder testSubOrder1 = new TestSubOrder();
		// testSubOrder1.setPriceAmount("1000");
		// testSubOrder1.setProductCode("100033");
		// TestSubOrder testSubOrder2 = new TestSubOrder();
		// testSubOrder2.setPriceAmount("10000");
		// testSubOrder2.setProductCode("100034");
		// List bList = new ArrayList();
		// bList.add(testSubOrder);
		// bList.add(testSubOrder1);
		// bList.add(testSubOrder2);
		// TestOrder testOrder = new TestOrder();
		// testOrder.setOrderId("10023");
		// testOrder.setAmount("100");
		// testOrder.setTestSubOrderList(bList);
		// TestOrder testOrder1 = new TestOrder();
		// testOrder1.setOrderId("10024");
		// testOrder1.setAmount("1000");
		// testOrder1.setTestSubOrderList(bList);
		// TestOrder testOrder2 = new TestOrder();
		// testOrder2.setOrderId("10025");
		// testOrder2.setAmount("10000");
		// testOrder2.setTestSubOrderList(bList);
		// List aList = new ArrayList();
		// aList.add(testOrder);
		// aList.add(testOrder1);
		// aList.add(testOrder2);
		//
		// System.out.println(JSON.toJSONString(aList));
	}

}
