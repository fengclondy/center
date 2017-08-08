package cn.htd.zeus.tc.common.util;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Purpose AES加密解密
 * @author zf.zhang
 * @since 2015-11-2 20:10
 * 
 */
public class AESAlgorithmUtil {
	public static void main(String[] args) {
		System.out.println((encrypt("queryCouponCount","986566561254568a47794d4950642227662a403f3b6F325e45774S5s44")));
		System.out.println(decrypt("135bb9b3e2a0c9d2c27c00fb7a12f3c356533f6e33481162f444d633da451a5f","986566561254568a47794d4950642227662a403f3b6F325e45774S5s44"));
	}
	private AESAlgorithmUtil() {
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(AESAlgorithmUtil.class);

	/**
	 * 获取二进制字节数组
	 * 
	 * @param type
	 * @param content
	 * @param key
	 * @return
	 */
	private static byte[] getResult(int type, String content, String key) {
		byte[] result = null;
		try {
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");  
	        secureRandom.setSeed(key.getBytes());
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(type, secretKeySpec);// 初始化
			if (Cipher.ENCRYPT_MODE == type) {
				result = cipher.doFinal(content.getBytes("utf-8"));
			} else if (Cipher.DECRYPT_MODE == type) {
				result = cipher.doFinal(parseHexStr2Byte(content));
			}

		} catch (Exception e) {
			LOGGER.error("excepiton:"+e);
		}
		return result;
	}

	/**
	 * 加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param key
	 *            加密密码
	 * @return String
	 */
	public static String encrypt(String content, String key) {
		try {
			return parseByte2HexStr(getResult(Cipher.ENCRYPT_MODE, content, key));
		} catch (Exception e) {
			LOGGER.error("加密异常！", e);
		}

		return null;

	}

	/**
	 * 解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return String
	 */
	public static String decrypt(String content, String key) {
		try {
			return new String(getResult(Cipher.DECRYPT_MODE, content, key),
					"UTF-8");
		} catch (Exception e) {
			LOGGER.error("解密异常！", e);
		}
		return null;
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	private static String parseByte2HexStr(byte buf[]) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toLowerCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	private static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1) {
			return null;
		}
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

}