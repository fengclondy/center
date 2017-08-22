package cn.htd.promotion.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.htd.promotion.cpc.biz.dao.BuyerBargainRecordDAO;
import cn.htd.promotion.cpc.biz.service.PromotionSloganService;

@Transactional  
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:applicationContext_test.xml","classpath:mybatis/sqlconfig/sqlMapConfig.xml"})  
public class PromotionSloganTestUnit {

    @Resource
    private PromotionSloganService promotionSloganService;
    
	@Resource
	private BuyerBargainRecordDAO buyerBargainRecordDAO;
    
	@Test
	public void savePromotionInfo() {
		Integer qty = buyerBargainRecordDAO.queryPromotionBargainJoinQTY("22172129400942");
		System.out.println(qty);
	}
}
