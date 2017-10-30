package cn.htd.usercenter.common.util;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MD5工具类
 * 
 * @author shihb
 */
public class Md5Utils {
    private static final Logger log = LoggerFactory.getLogger(Md5Utils.class);

    public static String getMd5(String str) {
        byte[] bs = null;
        try {
            bs = MessageDigest.getInstance("MD5").digest(str.getBytes());
        } catch (Exception e) {
            log.error("MD5解密出错", e);
            bs = str.getBytes();
        }
        StringBuilder sb = new StringBuilder(40);
        for (byte x : bs) {
            if ((x & 0xff) >> 4 == 0) {
                sb.append("0").append(Integer.toHexString(x & 0xff));
            } else {
                sb.append(Integer.toHexString(x & 0xff));
            }
        }
        return sb.toString();
    }
}
