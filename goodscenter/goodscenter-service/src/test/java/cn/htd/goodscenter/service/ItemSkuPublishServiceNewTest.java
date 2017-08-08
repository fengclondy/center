package cn.htd.goodscenter.service;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.dto.TradeInventoryInDTO;
import cn.htd.goodscenter.dto.TradeInventoryOutDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by thinkpad on 2017/1/5.
 */
public class ItemSkuPublishServiceNewTest {
    ApplicationContext ctx;
    private TradeInventoryExportService tradeInventoryExportService;

    @Before
    public void setUp() throws Exception {
        ctx = new ClassPathXmlApplicationContext("test.xml");
        tradeInventoryExportService = (TradeInventoryExportService) ctx.getBean("tradeInventoryExportService");
    }

    @Test
    public void testQueryTradeInventoryList(){
        TradeInventoryInDTO dto = new TradeInventoryInDTO();
        dto.setSellerId(1l);
        ExecuteResult<DataGrid<TradeInventoryOutDTO>> result = tradeInventoryExportService.queryTradeInventoryList(dto,null);
        System.out.println("heh");
    }
}
