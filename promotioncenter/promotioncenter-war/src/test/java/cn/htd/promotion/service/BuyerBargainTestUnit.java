package cn.htd.promotion.service;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import cn.htd.promotion.cpc.biz.service.BuyerLaunchBargainInfoService;
import cn.htd.promotion.cpc.biz.service.PromotionBargainInfoService;
import cn.htd.promotion.cpc.common.util.GenerateIdsUtil;
import cn.htd.promotion.cpc.dto.request.BuyerBargainLaunchReqDTO;
import cn.htd.promotion.cpc.dto.response.PromotionBargainInfoResDTO;

@Transactional  
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:applicationContext_test.xml","classpath:mybatis/sqlconfig/sqlMapConfig.xml"})  
public class BuyerBargainTestUnit {
	
	@Resource
	private BuyerLaunchBargainInfoService buyerLaunchBargainInfoService;
	
	@Resource
	private PromotionBargainInfoService promotionBargainInfoService;
	
    @Before  
    public void setUp() throws Exception {  
    	
    }
    
//    @Test
//    @Rollback(false) 
//    public void testGetBuyerLaunchBargainInfoByBuyerCode() {
//    	try {
//			String buyerCode = "htd20070002";
//			String messageId = GenerateIdsUtil.generateId(GenerateIdsUtil.getHostIp());
//			List<BuyerLaunchBargainInfoResDTO> list = buyerLaunchBargainInfoService.getBuyerLaunchBargainInfoByBuyerCode(buyerCode,messageId);
//			System.out.println(JSON.toJSONString(list));
//    	} catch (Exception e) {
//    		
//		}
//    }
    
    @Test
    @Rollback(false) 
    public void testGetPromotionBargainInfoDetail() {
    	try {
    		BuyerBargainLaunchReqDTO buyerBargainLaunch = new BuyerBargainLaunchReqDTO();
			String messageId = GenerateIdsUtil.generateId(GenerateIdsUtil.getHostIp());
			buyerBargainLaunch.setPromotionId("123");
			buyerBargainLaunch.setLevelCode("1");
			buyerBargainLaunch.setMessageId(messageId);
			PromotionBargainInfoResDTO promotionBargainInfo = promotionBargainInfoService.getPromotionBargainInfoDetail(buyerBargainLaunch);
			System.out.println(JSON.toJSONString(promotionBargainInfo));
    	} catch (Exception e) {
    		
		}
    }
}
