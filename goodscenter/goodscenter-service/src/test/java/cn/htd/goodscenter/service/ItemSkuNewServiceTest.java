package cn.htd.goodscenter.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.ExecuteResult;
import cn.htd.goodscenter.dto.ItemArrivalNotifyDTO;
import cn.htd.goodscenter.dto.indto.JudgeRecevieAddressInDTO;
import cn.htd.goodscenter.dto.outdto.QueryFlashbuyItemSkuOutDTO;
import cn.htd.goodscenter.test.common.CommonTest;

/**
 * Created by thinkpad on 2016/12/30.
 */
public class ItemSkuNewServiceTest extends CommonTest {
    ApplicationContext ctx;
    private ItemSkuExportService itemSkuExportService;


    @Before
    public void setUp() throws Exception {
        ctx = new ClassPathXmlApplicationContext("test.xml");
        itemSkuExportService = (ItemSkuExportService) ctx.getBean("itemSkuExportService");
    }

    @Test
    public void testQueryItemSkuListByCondition(){

        ExecuteResult<QueryFlashbuyItemSkuOutDTO> executeResult = itemSkuExportService.queryItemSkuBySkuCode("1000009335");
        System.out.println("hehe");
    }
    
    @Test
    public void testIsRecevieAddressOutOfSaleRange(){
    	JudgeRecevieAddressInDTO  judgeRecevieAddressInDTO=new JudgeRecevieAddressInDTO();
    	judgeRecevieAddressInDTO.setCityCode("021");
    	judgeRecevieAddressInDTO.setDistrictCode("320104");
    	judgeRecevieAddressInDTO.setProvinceCode("32");
    	judgeRecevieAddressInDTO.setSkuCode("1000006898");
    	judgeRecevieAddressInDTO.setIsBoxFlag("0");
    	itemSkuExportService.isRecevieAddressInSaleRange(judgeRecevieAddressInDTO);
    }
    
    @Test
    public void testSaveItemArrivalNotify(){
    	ItemArrivalNotifyDTO itemArrivalNotifyDTO=new ItemArrivalNotifyDTO();
    	itemArrivalNotifyDTO.setBuyerId(1L);
    	itemArrivalNotifyDTO.setIsBoxFlag("0");
    	itemArrivalNotifyDTO.setItemId(1000012111L);
    	itemArrivalNotifyDTO.setNotifyMobile(15371636185L);
    	itemArrivalNotifyDTO.setOperatorId(1L);
    	itemArrivalNotifyDTO.setOperatorName("测试用户");
    	itemArrivalNotifyDTO.setSellerId(2L);
    	itemArrivalNotifyDTO.setShopId(2000000354L);
    	itemArrivalNotifyDTO.setSkuId(1000000579L);
    	itemSkuExportService.saveItemArrivalNotify(itemArrivalNotifyDTO);
    }
    
    @Test
    public void testQueryOrderImportItemInfo(){
    	itemSkuExportService.queryOrderImportItemInfo("1000000003");
    }

}
