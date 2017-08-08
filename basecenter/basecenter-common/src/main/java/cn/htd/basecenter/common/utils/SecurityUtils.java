package cn.htd.basecenter.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtils {

	public static MessageDigest md;

	static {
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public static String md5To32(String str) {
		md.update(str.getBytes());
		byte b[] = md.digest();
		int i;
		StringBuilder builder = new StringBuilder();
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				builder.append("0");
			builder.append(Integer.toHexString(i).toUpperCase());
		}
		return builder.toString();
	}

}
