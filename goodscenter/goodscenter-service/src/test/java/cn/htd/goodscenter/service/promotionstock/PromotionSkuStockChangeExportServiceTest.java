package cn.htd.goodscenter.service.promotionstock;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.dto.stock.PromotionStockChangeDTO;
import cn.htd.goodscenter.test.common.CommonTest;

public class PromotionSkuStockChangeExportServiceTest extends CommonTest{

    @Autowired
    private PromotionSkuStockChangeExportService promotionSkuStockChangeExportService;
    
    @Test
    public void testSkuStockChangeExportService0() {
    	 PromotionStockChangeDTO promotionStockChangeDTO = new PromotionStockChangeDTO();
    	 promotionStockChangeDTO.setSkuCode("HTD_0000080468");
    	 promotionStockChangeDTO.setQuantity(3);
    	 promotionStockChangeDTO.setMessageId(UUID.randomUUID().toString());
    	 PromotionStockChangeDTO promotionStockChangeDTO2 = new PromotionStockChangeDTO();
    	 promotionStockChangeDTO2.setSkuCode("HTD_0000078834");
    	 promotionStockChangeDTO2.setQuantity(3);
    	 promotionStockChangeDTO2.setMessageId(UUID.randomUUID().toString());
         List<PromotionStockChangeDTO> list = new ArrayList();
         list.add(promotionStockChangeDTO);
         list.add(promotionStockChangeDTO2);
         ExecuteResult<String> executeResult = promotionSkuStockChangeExportService.batchReduceStock(list);
         System.out.println(JSON.toJSONString(executeResult));
    }
    
    @Test
    public void testSkuStockChangeExportService1() {
    	 PromotionStockChangeDTO promotionStockChangeDTO = new PromotionStockChangeDTO();
    	 promotionStockChangeDTO.setSkuCode("HTD_0000080468");
    	 promotionStockChangeDTO.setQuantity(3);
    	 promotionStockChangeDTO.setMessageId(UUID.randomUUID().toString());
         List<PromotionStockChangeDTO> list = new ArrayList();
         list.add(promotionStockChangeDTO);
         ExecuteResult<String> executeResult = promotionSkuStockChangeExportService.batchReleaseStock(list);
         System.out.println(JSON.toJSONString(executeResult));
    }
}
