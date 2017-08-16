package cn.htd.promotion.service;


import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.htd.goodscenter.common.constants.ResultCodeEnum;
import cn.htd.promotion.cpc.dto.response.BuyerLaunchBargainInfoResDTO;
import cn.htd.zeus.tc.common.util.GenerateIdsUtil;

@Transactional  
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:applicationContext_test.xml","classpath:mybatis/sqlconfig/sqlMapConfig.xml"})  
public class BuyerBargainTestUnit {
	
	@Resource
	private BuyerLaunchBargainInfoService buyerLaunchBargainInfoService;
	
    @Before  
    public void setUp() throws Exception {  
    	
    }
    
    @Test
    @Rollback(false) 
    public void testGetBuyerLaunchBargainInfoByBuyerCode() {
    	try {
			String buyerCode = "htd20070002";
			String messageId = GenerateIdsUtil.generateId(GenerateIdsUtil.getHostIp());
			List<BuyerLaunchBargainInfoResDTO> list = buyerLaunchBargainInfoService.getBuyerLaunchBargainInfoByBuyerCode(buyerCode,messageId);
			String resultCode = tradeOrdersDMO.getResultCode();
			assertEquals(resultCode, ResultCodeEnum.SUCCESS.getCode());  
    	} catch (Exception e) {
		}
    }
}
