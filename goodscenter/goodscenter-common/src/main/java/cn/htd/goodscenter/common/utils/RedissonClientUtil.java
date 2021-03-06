package cn.htd.goodscenter.common.utils;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;

/**
 * Redisson客户端获取工具
 */
public class RedissonClientUtil {
    /**
     * RedissonClient
     */
    private RedissonClient redissonClient;

    private String redisAddress;

    private int connectionPoolSize;

    /**
     * spring启动时初始化
     */
    private void init() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress(redisAddress);
        singleServerConfig.setConnectionPoolSize(connectionPoolSize);
        redissonClient = Redisson.create(config);
    }

    public RedissonClient getInstance() {
        return redissonClient;
    }

    public void setRedisAddress(String redisAddress) {
        this.redisAddress = redisAddress;
    }

    public void setConnectionPoolSize(int connectionPoolSize) {
        this.connectionPoolSize = connectionPoolSize;
    }
}
