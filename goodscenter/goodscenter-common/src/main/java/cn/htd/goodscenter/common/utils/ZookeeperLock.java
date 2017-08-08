package cn.htd.goodscenter.common.utils;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * zookeeper 分布式锁工具类
 * 原理 ：基于zookeeper 的观察者模式 和 EPHEMERAL_SEQUENTIAL类型节点的特点。
 *      EPHEMERAL_SEQUENTIAL的特点是如果目录节点存在则会自动往后编号。
 * 实现 ：多个client抢锁时，在根节点下创建子节点（名称以竞争资源锁为名称），因为EPHEMERAL_SEQUENTIAL类型节点
 * 的特点，每个子节点名称会带上序号。此时每个client去查询root下的所有子节点，排序后，如果自己是最小的节点，则获取了锁，
 * 否则client线程阻塞，并去监听序号比自己小1的子节点，一旦比自己小1的子节点被delete,client会收到通知解除当前线程阻塞的状态。
 *
 * @author chenakng
 */
public class ZookeeperLock implements Watcher {
    /**
     * zk实例
     */
    private ZooKeeper zooKeeper = null;

    /**
     * 客户端和zk连接的超时时间。超时自动删除临时节点
     */
    private int timeout = 10000;

    /**
     * 根节点名称
     */
    private String rootPath = "/zeusGoodsCenterLockRootPath";

    /**
     * 当前节点路径
     * 结构： rootPath + / + lockName + -lock- + 序号
     */
    private String currentNodePath;

    /**
     * lockName和节点序号的分隔符  ↑
     */
    private static final String SEPARATOR = "-lock-";

    /**
     * 监听节点名称
     * 比自己小1的节点名称
     */
    private String listenNodePath;

    /**
     * 竞争资源，分布式共享锁
     */
    private String lockName;

    /**
     * 计数器
     */
    private CountDownLatch countDownLatch;

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(ZookeeperLock.class);

    public ZookeeperLock(String config, String lockName) {
        this.lockName = lockName;
        // 连接zooKeeper
        try {
            // 连接zooKeeper
            zooKeeper = new ZooKeeper(config, timeout, this);
            Stat stat = zooKeeper.exists(rootPath, false); //不监听
            if (stat == null) {  // 判断根节点是否存在
                // 创建PERSISTENT 类型路径 持久类型
                zooKeeper.create(rootPath, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            logger.info("connect zookeeper success");
        } catch (Exception e) {
            logger.error("zookeeperLock error", e);
            zooKeeper = null;
        }
    }

    /**
     * 获取锁
     */
    public void lock() {
        try {
            if (zooKeeper == null) {
                throw new RuntimeException("zooKeeper init error.");
            }
            if (!tryLock()) {
                wait4Lock();
            }
            // 得到锁
        } catch (Exception e) {
            logger.error("lock error,", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 等待锁
     * @throws KeeperException
     * @throws InterruptedException
     */
    private void wait4Lock() throws KeeperException, InterruptedException {
        // 没有取到锁,监听比自己小1的节点
        Stat stat = zooKeeper.exists(listenNodePath, true);
        if (stat != null) {
            // 构造为1的CountDownLatch
            countDownLatch = new CountDownLatch(1); // 计数为1
            // 线程阻塞，直到计数为0
            countDownLatch.await();  // 直到event事件将CountDownLatch变为0
            logger.info("wait lock listenNodePath : {}, currentNodePath : {}", listenNodePath, currentNodePath);
        }
    }

    /**
     * 监听
     *
     * @param event
     */
    @Override
    public void process(WatchedEvent event) {
        logger.info("listen listenNodePath:{}, eventType", listenNodePath, event.getType());
        // 得到通知
        if (countDownLatch != null && event.getType().equals(Event.EventType.NodeDeleted)) {
            countDownLatch.countDown(); // 计数减一
        }
        logger.info("get lock , currentNodePath :{}, for listenNodePath : {} is deleted", currentNodePath, listenNodePath);
    }

    /**
     * 尝试获取锁
     * @return 是否获得锁
     */
    private boolean tryLock() {
        // 创建临时-自增节点
        try {
            // 临时节点结构 /zeusGoodsCenterLockRootPath/test-lock-000000000   序号按照创建顺序往后排
            currentNodePath = zooKeeper.create(rootPath + "/" + lockName + SEPARATOR, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            logger.info(" create node : {}", currentNodePath);
            // 获取所有子节点
            List<String> nodeList = zooKeeper.getChildren(rootPath, false);
            //取出所有lockName的锁
            List<String> currentLockNodes = new ArrayList<String>();
            for (String node : nodeList) {
                if(node.contains(lockName)){ // 如果是该lockName的所有子节点
                    currentLockNodes.add(node);
                }
            }
            // 排序、获取最小的节点。
            Collections.sort(currentLockNodes);
            //如果此线程创建的节点是最小的节点，则取得了锁。
            if (currentNodePath.equals(rootPath + "/" + currentLockNodes.get(0))) {
                logger.info("get lock : " + currentNodePath);
                return true;
            } else {
                String subNodeName = currentNodePath.substring(rootPath.length() + 1);
                int index = currentLockNodes.indexOf(subNodeName);
                // 否则监听比自己节点序号小1的节点
                listenNodePath = rootPath + "/" + currentLockNodes.get(index - 1);
                logger.info("wait lock, currentNodePath : {},  listenNodePath : {}, current index : {}", currentNodePath, listenNodePath ,index);
            }
        } catch (Exception e) {
            logger.error("lock error,", e);
        }
        return false;
    }

    public void unlock() {
        if (zooKeeper == null) {
            throw new RuntimeException("zooKeeper init error.");
        }
        try {
            zooKeeper.delete(currentNodePath, -1);
            logger.info("unlock, currentNodePath : {}", currentNodePath);
        } catch (Exception e) {
            logger.error("unlock error", e);
        } finally {
            try {
                zooKeeper.close();
                logger.info("zooKeeper close, currentNodePath : {}", currentNodePath);
            } catch (InterruptedException e) {
                logger.error("close zooKeeper error", e);
            }
        }
    }
}