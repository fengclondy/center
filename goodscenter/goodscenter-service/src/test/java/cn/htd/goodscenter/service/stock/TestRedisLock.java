package cn.htd.goodscenter.service.stock;

import cn.htd.goodscenter.common.utils.RedissonClientUtil;
import cn.htd.goodscenter.dto.stock.Order4StockChangeDTO;
import cn.htd.goodscenter.dto.stock.Order4StockEntryDTO;
import net.sf.jsqlparser.statement.execute.Execute;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by admin on 2016/12/22.
 */
public class TestRedisLock implements Runnable {

    private RedissonClientUtil redissonClientUtil;

    private SkuStockChangeExportService skuStockChangeExportService;

    TestRedisLock(RedissonClientUtil redissonClientUtil, SkuStockChangeExportService skuStockChangeExportService) {
        this.redissonClientUtil = redissonClientUtil;
        this.skuStockChangeExportService = skuStockChangeExportService;
    }

    @Override
    public void run() {
        RedissonClient client = redissonClientUtil.getInstance();
        RLock lock = client.getLock("anyLock");
        System.out.println(Thread.currentThread().getName() + "：进入");
        lock.lock();
        try {
            Order4StockChangeDTO order4StockChangeDTO = new Order4StockChangeDTO();
            order4StockChangeDTO.setOrderNo("1000000");
            order4StockChangeDTO.setOrderResource("web");
            List<Order4StockEntryDTO> list = new ArrayList();
            Order4StockEntryDTO order4StockEntryDTO = new Order4StockEntryDTO();
            order4StockEntryDTO.setQuantity(1);
            list.add(order4StockEntryDTO);
            order4StockChangeDTO.setOrderEntries(list);
            skuStockChangeExportService.reserveStock(order4StockChangeDTO);
            System.out.println(Thread.currentThread().getName() + "：获取锁");
        } finally {
            System.out.println(Thread.currentThread().getName() + "：释放锁");
            lock.unlock();
        }
    }
}
