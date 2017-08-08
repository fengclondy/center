package cn.htd.goodscenter.service;

import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.dto.venus.indto.VenusItemSkuPublishInDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinkpad on 2017/1/3.
 */
public class ItemSkuPublishServiceTest {

    ApplicationContext ctx;
    private TradeInventoryExportService tradeInventoryExportService;

    @Before
    public void setUp() throws Exception {
        ctx = new ClassPathXmlApplicationContext("test.xml");
        tradeInventoryExportService = (TradeInventoryExportService) ctx.getBean("tradeInventoryExportService");
    }

    @Test
    public void testSaveSkuInventoryList(){
        VenusItemSkuPublishInDTO dto = new VenusItemSkuPublishInDTO();
        List<VenusItemSkuPublishInDTO> list = new ArrayList<VenusItemSkuPublishInDTO>();
        dto.setSkuId(123l);
        dto.setItemId(222l);
        dto.setSkuCode("999");
        dto.setNote("haha");
        dto.setDisplayQty("1000");
        dto.setMinBuyQty("99");
        dto.setMaxPurchaseQty("100");
        dto.setShippingCost("23.5");
        dto.setCreateId(999l);
        dto.setCreateName("周杰伦");
        dto.setModifyId(123l);
        dto.setModifyName("刘德华");
        list.add(dto);
        ExecuteResult<String> result = tradeInventoryExportService.saveSkuInventoryList(list);
        System.out.println("hehe");
    }
}
