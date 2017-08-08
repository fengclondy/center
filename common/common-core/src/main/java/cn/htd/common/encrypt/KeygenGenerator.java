package cn.htd.common.encrypt;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 秘钥生成器
 * 
 */
public class KeygenGenerator {

	private static String[] chars = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D",
			"E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
			"Z" };

	/**
	 * 将固定字符串转换为ASC加密的秘钥
	 * 
	 * @param password
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 */
	public static SecretKeySpec getASCKey(String password) throws NoSuchAlgorithmException, NoSuchPaddingException {
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(password.getBytes());
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128, random);
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		return key;
	}

	/**
	 * 获得UID
	 * 
	 * @return
	 */
	public static String getUidKey() {
		UUID uuid = UUID.randomUUID();
		String a = uuid.toString().toUpperCase();
		return a;
	}

	public static void main(String args[]) throws Exception {
		// for (int i = 0; i < 1000; i++) {
		// String myGUID = generateVerifyNum();
		// System.out.println(i + ":" + myGUID);
		//
		// }
	}
}
