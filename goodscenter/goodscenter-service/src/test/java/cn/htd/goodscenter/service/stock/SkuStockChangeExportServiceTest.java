package cn.htd.goodscenter.service.stock;

import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.common.utils.RedissonClientUtil;
import cn.htd.goodscenter.dto.stock.Order4StockChangeDTO;
import cn.htd.goodscenter.dto.stock.Order4StockEntryDTO;
import cn.htd.goodscenter.dto.stock.StockTypeEnum;
import cn.htd.goodscenter.test.common.CommonTest;
import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by admin on 2016/12/12.
 */
public class SkuStockChangeExportServiceTest extends CommonTest {

    @Autowired
    private SkuStockChangeExportService skuStockChangeExportService;

    @Autowired
    private RedissonClientUtil redissonClientUtil;

    @Test
    public void testSkuStockChangeExportService0() {
        Executor executor = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 60 ; i++) {
            executor.execute(new TestRedisLock(redissonClientUtil, skuStockChangeExportService));
        }
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSkuStockChangeExportService() {
        Order4StockChangeDTO order4StockChangeDTO = new Order4StockChangeDTO();
        order4StockChangeDTO.setOrderNo("1017081110020402790");
        order4StockChangeDTO.setOrderResource("web");
        order4StockChangeDTO.setMessageId(UUID.randomUUID().toString());
        List<Order4StockEntryDTO> list = new ArrayList();
        Order4StockEntryDTO order4StockEntryDTO = new Order4StockEntryDTO();
        order4StockEntryDTO.setQuantity(5);
        order4StockEntryDTO.setSkuCode("HTDH_0000098400");
        order4StockEntryDTO.setIsBoxFlag(1);
        list.add(order4StockEntryDTO);
        order4StockChangeDTO.setOrderEntries(list);
        ExecuteResult<String> executeResult = skuStockChangeExportService.reserveStock(order4StockChangeDTO);
        System.out.println(JSON.toJSONString(executeResult));
    }


    @Test
    public void testSkuStockChangeExportService1() {
        Order4StockChangeDTO order4StockChangeDTO = new Order4StockChangeDTO();
        order4StockChangeDTO.setOrderNo("1017081110020402790");
        order4StockChangeDTO.setOrderResource("web");
        order4StockChangeDTO.setMessageId(UUID.randomUUID().toString());
        List<Order4StockEntryDTO> list = new ArrayList();
        Order4StockEntryDTO order4StockEntryDTO = new Order4StockEntryDTO();
        order4StockEntryDTO.setQuantity(2);
        order4StockEntryDTO.setSkuCode("HTDH_0000098400");
        order4StockEntryDTO.setIsBoxFlag(1);
        order4StockEntryDTO.setStockTypeEnum(StockTypeEnum.RELEASE);
        list.add(order4StockEntryDTO);
        order4StockChangeDTO.setOrderEntries(list);
        ExecuteResult<String> executeResult = skuStockChangeExportService.releaseStock(order4StockChangeDTO);
        System.out.println(JSON.toJSONString(executeResult));
    }

    @Test
    public void testSkuStockChangeExportService2() {
        Order4StockChangeDTO order4StockChangeDTO = new Order4StockChangeDTO();
        order4StockChangeDTO.setOrderNo("1017081110020402790");
        order4StockChangeDTO.setOrderResource("web");
        order4StockChangeDTO.setMessageId(UUID.randomUUID().toString());
        List<Order4StockEntryDTO> list = new ArrayList();
        Order4StockEntryDTO order4StockEntryDTO = new Order4StockEntryDTO();
        order4StockEntryDTO.setQuantity(3);
        order4StockEntryDTO.setSkuCode("HTDH_0000098400");
        order4StockEntryDTO.setIsBoxFlag(1);
        order4StockEntryDTO.setStockTypeEnum(StockTypeEnum.REDUCE);
        list.add(order4StockEntryDTO);
        order4StockChangeDTO.setOrderEntries(list);
        ExecuteResult<String> executeResult = skuStockChangeExportService.reduceStock(order4StockChangeDTO);
        System.out.println(JSONObject.fromObject(executeResult));
    }



    @Test
    public void testChangePrice() {
        Order4StockChangeDTO order4StockChangeDTO = new Order4StockChangeDTO();
        order4StockChangeDTO.setOrderNo("1017080910020402766");
        order4StockChangeDTO.setOrderResource("web");
        order4StockChangeDTO.setMessageId(UUID.randomUUID().toString());
        List<Order4StockEntryDTO> list = new ArrayList();
        Order4StockEntryDTO order4StockEntryDTO = new Order4StockEntryDTO();
        order4StockEntryDTO.setQuantity(0);
        order4StockEntryDTO.setSkuCode("HTDH_0000098300");
        order4StockEntryDTO.setIsBoxFlag(0);
        order4StockEntryDTO.setStockTypeEnum(StockTypeEnum.RESERVE);
        Order4StockEntryDTO order4StockEntryDTO2 = new Order4StockEntryDTO();
        order4StockEntryDTO2.setQuantity(6);
        order4StockEntryDTO2.setSkuCode("HTDH_0000098400");
        order4StockEntryDTO2.setIsBoxFlag(1);
        order4StockEntryDTO2.setStockTypeEnum(StockTypeEnum.RESERVE);
        list.add(order4StockEntryDTO);
        list.add(order4StockEntryDTO2);
        order4StockChangeDTO.setOrderEntries(list);
        ExecuteResult<String> executeResult = skuStockChangeExportService.changePriceStock(order4StockChangeDTO);
        System.out.println(JSONObject.fromObject(executeResult));
    }


    @Test
    public void testSkuStockChangeExportService3() {
        Order4StockChangeDTO order4StockChangeDTO = new Order4StockChangeDTO();
        order4StockChangeDTO.setOrderNo("1495100379527");
        order4StockChangeDTO.setOrderResource("web");
        order4StockChangeDTO.setMessageId(UUID.randomUUID().toString());
        List<Order4StockEntryDTO> list = new ArrayList();
        Order4StockEntryDTO order4StockEntryDTO = new Order4StockEntryDTO();
        order4StockEntryDTO.setQuantity(1);
        order4StockEntryDTO.setSkuCode("HTD_0000069313");
        order4StockEntryDTO.setIsBoxFlag(1);
        order4StockEntryDTO.setStockTypeEnum(StockTypeEnum.ROLLBACK);
        list.add(order4StockEntryDTO);
        order4StockChangeDTO.setOrderEntries(list);
        ExecuteResult<String> executeResult = skuStockChangeExportService.rollbackStock(order4StockChangeDTO);
        System.out.println(JSONObject.fromObject(executeResult));
    }



    @Test
    public void testBatchSkuStockChangeExportService() {
        List<Order4StockChangeDTO> order4StockChangeDTOs = new ArrayList();


        Order4StockChangeDTO order4StockChangeDTO = new Order4StockChangeDTO();
        order4StockChangeDTO.setOrderNo("1000000012");
        order4StockChangeDTO.setOrderResource("web");
        order4StockChangeDTO.setMessageId("8172731238383737131");
        List<Order4StockEntryDTO> list = new ArrayList();
        Order4StockEntryDTO order4StockEntryDTO = new Order4StockEntryDTO();
        order4StockEntryDTO.setQuantity(1);
        order4StockEntryDTO.setSkuCode("1000006967");
        order4StockEntryDTO.setIsBoxFlag(0);
        list.add(order4StockEntryDTO);
        order4StockChangeDTO.setOrderEntries(list);

        order4StockChangeDTOs.add(order4StockChangeDTO);
        ExecuteResult<String> executeResult = skuStockChangeExportService.batchReserveStock(order4StockChangeDTOs);
        System.out.println("结果：" + net.sf.json.JSONObject.fromObject(executeResult));
    }

}
