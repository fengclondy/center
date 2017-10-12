package cn.htd.promotion.cpc.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @version V1.0
 * @Title: GeneratorUtils.java
 * @Description: 生成各类业务ID
 * @date 2015年9月1日 下午11:43:02
 **/
@Component("keyGeneratorUtils")
public class KeyGeneratorUtils {
    // RedisMessageId数据
    private static final String REDIS_MESSAGE_ID_KEY = "B2C_MIDDLE_MESSAGEID_MSB_SEQ";
    
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * ID生成 23位，13位时间戳+6位IP4后两段左补0+4位循环（0001-9999）左补0
     *
     * @return
     */
    public String generateMessageId() {

        String id = "";
        String ip = "";
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

    private String getCacheSeq(String seqKey, Long maxValue) {
        Long seqIndexLong = redisTemplate.opsForValue().increment(seqKey, 1L);
        seqIndexLong = seqIndexLong % maxValue;
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
//        Long dateString = 100000000000L;
//        System.out.println(String.format("%0" + dateString.toString().length() + "d", 120));
        long t = System.currentTimeMillis();//获得当前时间的毫秒数
        Random rd = new Random(t);//作为种子数传入到Random的构造器中
      	System.out.println(rd.nextInt());//生成随即整数

    }
}
