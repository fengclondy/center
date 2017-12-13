package cn.htd.marketcenter.common.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ThreadExecutorService {

    private volatile static ThreadExecutorService instance = null;
    // 设置默认初始线程池的大小
    private static final int COREPOOLSIZE = 24;
    private static final int MAXIMUMPOOLSIZE = 50;
    private static final int QUEUESIZE = 500;
    private static final long KEEPLIVETIME = 60L;
    private static final BlockingQueue<Runnable> QUEUE = new ArrayBlockingQueue<Runnable>(QUEUESIZE);
    private static final RejectedExecutionHandler CALLERRUNSHANDLER = new CallerRunsPolicy();
    private ExecutorService boundedThreadPool;

    /**
     * 提供唯一实例
     *
     * @return
     */
    public static ThreadExecutorService getInstance() {
        if (instance == null) {
            synchronized (ThreadExecutorService.class) {
                if (instance == null) {
                    instance = new ThreadExecutorService();
                }
            }
        }
        return instance;
    }

    private ThreadExecutorService() {
        // 创建有界大小的线程池
        // corePoolSize： 线程池维护线程的最少数量

        // maximumPoolSize：线程池维护线程的最大数量

        // keepLiveTime： 线程池维护线程所允许的空闲时间

        // TimeUnit.SECONDS： 线程池维护线程所允许的空闲时间的单位

        // queue： 线程池所使用的缓冲队列

        // callerRunsHandler： 线程池对拒绝任务的处理策略
        boundedThreadPool = new ThreadPoolExecutor(COREPOOLSIZE, MAXIMUMPOOLSIZE, KEEPLIVETIME, TimeUnit.SECONDS, QUEUE,
                CALLERRUNSHANDLER);
    }

    public ExecutorService getBoundedThreadPool() {
        return boundedThreadPool;
    }
}