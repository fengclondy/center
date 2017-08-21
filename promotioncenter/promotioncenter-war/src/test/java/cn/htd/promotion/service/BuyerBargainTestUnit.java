package cn.htd.promotion.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.htd.promotion.cpc.biz.service.BuyerBargainRecordService;
import cn.htd.promotion.cpc.biz.service.BuyerLaunchBargainInfoService;
import cn.htd.promotion.cpc.biz.service.PromotionBargainInfoService;
import cn.htd.promotion.cpc.dto.request.BuyerBargainRecordReqDTO;
import cn.htd.promotion.cpc.dto.response.BuyerLaunchBargainInfoResDTO;
import com.alibaba.fastjson.JSON;
import cn.htd.promotion.cpc.dto.request.BuyerBargainLaunchReqDTO;

@Transactional  
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:applicationContext_test.xml","classpath:mybatis/sqlconfig/sqlMapConfig.xml"})  
public class BuyerBargainTestUnit {
	
	@Resource
	private BuyerLaunchBargainInfoService buyerLaunchBargainInfoService;
	
	@Resource
	private PromotionBargainInfoService promotionBargainInfoService;
	
	@Resource
	private BuyerBargainRecordService buyerBargainRecordService;
	
    @Before  
    public void setUp() throws Exception {  
    	
    }
    
    @Test
    @Rollback(false) 
    public void testGetBuyerLaunchBargainInfoByBuyerCode() {
    	try {
			String buyerCode = "htd20070002";
			String messageId ="001";
			List<BuyerLaunchBargainInfoResDTO> list = buyerLaunchBargainInfoService.getBuyerLaunchBargainInfoByBuyerCode(buyerCode,messageId);
			System.out.println(JSON.toJSONString(list));
    	} catch (Exception e) {
    		
		}
    }
    
//    @Test
//    @Rollback(false) 
//    public void testGetPromotionBargainInfoDetail() {
//    	try {
//    		BuyerBargainLaunchReqDTO buyerBargainLaunch = new BuyerBargainLaunchReqDTO();
//			String messageId = GenerateIdsUtil.generateId(GenerateIdsUtil.getHostIp());
//			buyerBargainLaunch.setPromotionId("123");
//			buyerBargainLaunch.setLevelCode("1");
//			buyerBargainLaunch.setMessageId(messageId);
//			buyerBargainLaunch.setBargainCode("123");
//			PromotionBargainInfoResDTO promotionBargainInfo = promotionBargainInfoService.getPromotionBargainInfoDetail(buyerBargainLaunch);
//			System.out.println(JSON.toJSONString(promotionBargainInfo));
//    	} catch (Exception e) {
//    		
//		}
//    }
    
    
//    @Test
//    @Rollback(false) 
//    public void testGetBuyerLaunchBargainInfoByBargainCode() {
//    	try {
//    		String bargainCode = "123";
//			String messageId = GenerateIdsUtil.generateId(GenerateIdsUtil.getHostIp());
//			List<BuyerBargainRecordResDTO> promotionBargainInfo = buyerBargainRecordService.getBuyerBargainRecordByBargainCode(bargainCode, messageId);
//			System.out.println(JSON.toJSONString(promotionBargainInfo));
//    	} catch (Exception e) {
//    		
//		}
//    }
    
    
    @Test
    @Rollback(false) 
    public void insertBuyerBargainRecord() {
    	try {
    		String messageId = "001";
    		BuyerBargainRecordReqDTO buyerBargainRecord = new BuyerBargainRecordReqDTO();
    		buyerBargainRecord.setBargainCode("123");
    		buyerBargainRecord.setHeadSculptureUrl("http://baidu.com");
    		buyerBargainRecord.setBargainPersonCode("htd20070002");
    		buyerBargainRecord.setBargainPresonName("帮忙砍价3");
    		buyerBargainRecord.setBargainAmount(new BigDecimal("5.23"));
    		buyerBargainRecord.setBargainTime(new Date());
    		buyerBargainRecord.setCreateId(123);
    		buyerBargainRecord.setCreateName("系统");
    		buyerBargainRecord.setCreateTime(new Date());
    		buyerBargainRecord.setMessageId(messageId);
			Integer i  = buyerBargainRecordService.insertBuyerBargainRecord(buyerBargainRecord);
			System.out.println(i);
    	} catch (Exception e) {
    		
    	}
    }
    		
//    @Test
//    @Rollback(false) 
//    public void insertBuyerBargainRecord() {
//    	try {
//    		String messageId = GenerateIdsUtil.generateId(GenerateIdsUtil.getHostIp());
//    		BuyerBargainRecordReqDTO buyerBargainRecord = new BuyerBargainRecordReqDTO();
//    		buyerBargainRecord.setBargainCode("123");
//    		buyerBargainRecord.setHeadSculptureUrl("http://baidu.com");
//    		buyerBargainRecord.setBargainPersonCode("htd20070002");
//    		buyerBargainRecord.setBargainPresonName("帮忙砍价3");
//    		buyerBargainRecord.setBargainAmount(new BigDecimal("5.23"));
//    		buyerBargainRecord.setBargainTime(new Date());
//    		buyerBargainRecord.setCreateId(123);
//    		buyerBargainRecord.setCreateName("系统");
//    		buyerBargainRecord.setCreateTime(new Date());
//    		buyerBargainRecord.setMessageId(messageId);
//			Integer i  = buyerBargainRecordService.insertBuyerBargainRecord(buyerBargainRecord);
//			System.out.println(i);
//    	} catch (Exception e) {
//    		
//		}
//    }
    
    
//  @Test
//  @Rollback(false) 
//  public void getThisPersonIsBargain() {
//  	try {
//  		String messageId = GenerateIdsUtil.generateId(GenerateIdsUtil.getHostIp());
//  		String bargainCode = "123";
//  		String bargainPersonCode = "htd20070001";
//  		Boolean flag  = buyerBargainRecordService.getThisPersonIsBargain(bargainCode, bargainPersonCode, messageId);
//		System.out.println(flag);
//  	} catch (Exception e) {
//  		
//		}
//  }
    
  @Test
  @Rollback(false) 
  public void updateBuyerLaunchBargainInfo() {
  	try {
  		String messageId = "";
  		BuyerBargainLaunchReqDTO buyerBargainLaunch = new BuyerBargainLaunchReqDTO();
  		buyerBargainLaunch.setLaunchTime(new Date());
  		buyerBargainLaunch.setBargainOverTime(new Date());
  		buyerBargainLaunch.setIsBargainOver(1);
  		buyerBargainLaunch.setGoodsCurrentPrice(new BigDecimal(233.22));
  		buyerBargainLaunch.setMessageId(messageId);
  		buyerBargainLaunch.setModifyId(125);
  		buyerBargainLaunch.setModifyName("测试账号");
  		buyerBargainLaunch.setModifyTime(new Date());
  		buyerBargainLaunch.setPromotionId("123");
  		buyerBargainLaunch.setLevelCode("1");
  		Integer flag  = buyerLaunchBargainInfoService.updateBuyerLaunchBargainInfo(buyerBargainLaunch);
		System.out.println(flag);
  	} catch (Exception e) {
		}
  }
    
    
}
