package cn.htd.zeus.tc.service.test;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.htd.goodscenter.common.constants.ResultCodeEnum;
import cn.htd.zeus.tc.biz.service.ShopOrderAnalysisReportService;
import cn.htd.zeus.tc.biz.service.ShopOrderStatisticsReportService;
import cn.htd.zeus.tc.dto.response.ShopOrderAnalsisDayReportResDTO;
import cn.htd.zeus.tc.dto.response.ShopOrderStatisticsDayReportResDTO;
import cn.htd.zeus.tc.dto.resquest.ShopOrderAnalysisDayReportReqDTO;
import cn.htd.zeus.tc.dto.resquest.ShopOrderStatisticsDayReportReqDTO;

@Transactional  
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:applicationContext_test.xml","classpath:mybatis/sqlconfig/sqlMapConfig.xml"})  
public class ShopOrderReportTestUnit {
	@Resource
	private ShopOrderAnalysisReportService shopOrderAnalysisReportService;
	
	@Resource
	private ShopOrderStatisticsReportService shopOrderStatisticsReportService;
	
    @Before  
    public void setUp() throws Exception 
    {  
    }
    
    @Test
    @Rollback(false) 
    public void testShopOrderAnalysisDayReport()
    {
    	try {
    		ShopOrderAnalysisDayReportReqDTO shopOrderAnalysisDayReportReqDTO = new ShopOrderAnalysisDayReportReqDTO();
    		shopOrderAnalysisDayReportReqDTO.setShopId(1);
    		shopOrderAnalysisDayReportReqDTO.setStartTime(20170219);
    		shopOrderAnalysisDayReportReqDTO.setEndTime(20170221);
    		shopOrderAnalysisDayReportReqDTO.setPage(0);
    		shopOrderAnalysisDayReportReqDTO.setLimit(10);
    		ShopOrderAnalsisDayReportResDTO shopOrderAnalsisDayReportResDTO = shopOrderAnalysisReportService.queryShopOrderAnalsisDayReportList(shopOrderAnalysisDayReportReqDTO);
			String resultCode = shopOrderAnalsisDayReportResDTO.getResponseCode();
			assertEquals(resultCode, ResultCodeEnum.SUCCESS.getCode());  
    	} catch (Exception e) {
		}
    }
    
    @Test
    @Rollback(false) 
    public void testShopOrderStatisticsDayReport()
    {
    	try {
    		ShopOrderStatisticsDayReportReqDTO shopOrderStatisticsDayReportReqDTO = new ShopOrderStatisticsDayReportReqDTO();
    		shopOrderStatisticsDayReportReqDTO.setShopId(1);
    		shopOrderStatisticsDayReportReqDTO.setStartTime(20170219);
    		shopOrderStatisticsDayReportReqDTO.setEndTime(20170221);
    		ShopOrderStatisticsDayReportResDTO shopOrderStatisticsDayReportResDTO = shopOrderStatisticsReportService.queryShopOrderStatisticsDayReportList(shopOrderStatisticsDayReportReqDTO);
			String resultCode = shopOrderStatisticsDayReportResDTO.getResponseCode();
			assertEquals(resultCode, ResultCodeEnum.SUCCESS.getCode());  
    	} catch (Exception e) {
		}
    }
}
