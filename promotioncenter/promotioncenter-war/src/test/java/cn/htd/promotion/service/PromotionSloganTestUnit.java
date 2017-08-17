package cn.htd.promotion.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.htd.promotion.cpc.biz.service.PromotionSloganService;
import cn.htd.promotion.cpc.common.util.GenerateIdsUtil;
import cn.htd.promotion.cpc.dto.response.PromotionSloganResDTO;

import com.alibaba.fastjson.JSON;

@Transactional  
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:applicationContext_test.xml","classpath:mybatis/sqlconfig/sqlMapConfig.xml"})  
public class PromotionSloganTestUnit {

	@Resource
	PromotionSloganService promotionSloganService;

	@Test
	@Rollback(false) 
	public void testPromotionSloganList() {
		try {
			String providerSellerCode = "123";
			System.out.println(111);
			String messageId = GenerateIdsUtil.generateId(GenerateIdsUtil.getHostIp());
			List<PromotionSloganResDTO> list = promotionSloganService
					.queryBargainSloganBySellerCode(providerSellerCode, messageId);
			System.out.println(JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
