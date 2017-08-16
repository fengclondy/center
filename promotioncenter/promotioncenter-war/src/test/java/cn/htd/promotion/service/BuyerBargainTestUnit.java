package cn.htd.promotion.service;

import java.util.List;

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
import cn.htd.promotion.cpc.common.util.GenerateIdsUtil;
import cn.htd.promotion.cpc.dto.response.BuyerLaunchBargainInfoResDTO;

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
			System.out.println(JSON.toJSONString(list));
    	} catch (Exception e) {
		}
    }
}
