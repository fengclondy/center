package cn.htd.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import cn.htd.common.dao.util.RedisDB;

public class MessageIdUtils {
		// RedisMessageId数据
		private static final String REDIS_MESSAGE_ID_KEY = "B2B_MIDDLE_MESSAGEID_MSB_SEQ";

		/**
		 * 带自定义前缀的ID生成
		 * 
		 * @param prefixKey 自定义的0-7位前缀
		 * @param ip
		 * @return
		 */
		public static String customGenerateId(String prefixKey) {
			return prefixKey + generateMessageId();
		}
		

		/**
		 * ID生成 23位，13位时间戳+6位IP4后两段左补0+4位循环（0001-9999）左补0
		 * 
		 * @param ip
		 * @return
		 */
		public static String generateMessageId() {

			String id = null;
			try {
				id = String.valueOf(System.currentTimeMillis());
				String ip = getHostIp();
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

		private static String getCacheSeq(String seqKey, Long maxValue) {
			RedisDB redisDB = SpringApplicationContextHolder.getBean(RedisDB.class);
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
		private static String getHostIp() throws UnknownHostException {
			String ip = InetAddress.getLocalHost().getHostAddress();
			return ip;
		}

}
