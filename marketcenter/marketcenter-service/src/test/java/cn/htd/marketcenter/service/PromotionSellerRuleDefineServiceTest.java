package cn.htd.marketcenter.service;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.marketcenter.dto.PromotionSellerRuleDefineDTO;

/**
 * Created by thinkpad on 2016/12/1.
 */
public class PromotionSellerRuleDefineServiceTest {
	ApplicationContext ctx;
	private PromotionSellerRuleDefineService promotionSellerRuleDefineService;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		promotionSellerRuleDefineService = (PromotionSellerRuleDefineService) ctx
				.getBean("promotionSellerRuleDefineService");
	}

	@Test
	public void addPromotionBuyerRuleTest() {
		PromotionSellerRuleDefineDTO promotionSellerRule = new PromotionSellerRuleDefineDTO();
		promotionSellerRule.setRuleTargetType("aaa");
		promotionSellerRule.setModifyTime(new Date());
		promotionSellerRule.setModifyName("bbb");
		promotionSellerRule.setModifyId(Long.valueOf(111));
		promotionSellerRule.setCreateId(Long.valueOf(222));
		promotionSellerRule.setCreateName("ccc");
		promotionSellerRule.setRuleId(Long.valueOf(444));
		promotionSellerRule.setCreateTime(new Date());
		promotionSellerRule.setTargetSellerType("1");
		promotionSellerRuleDefineService.addPromotionSellerRule(promotionSellerRule);
		System.out.print("ok");
	}
	//
	// @Test
	// public void updateByConditionTest() {
	// PromotionSellerRuleDefineDTO promotionSellerRule = new
	// PromotionSellerRuleDefineDTO();
	// promotionSellerRule.setRuleId(Long.valueOf(444));
	// promotionSellerRule.setTargetSellerType("2");
	// promotionSellerRuleDefineService.updateByCondition(promotionSellerRule);
	// System.out.print("ok");
	// }
	//
	// @Test
	// public void queryByConditionTest() {
	// PromotionSellerRuleDefineDTO promotionSellerRule = new
	// PromotionSellerRuleDefineDTO();
	// promotionSellerRule.setRuleTargetType("aaa");
	// promotionSellerRule.setModifyName("bbb");
	// DataGrid<PromotionSellerRuleDefineDTO> dataGrid =
	// promotionSellerRuleDefineService
	// .queryByCondition(promotionSellerRule, null);
	// System.out.print(dataGrid.getRows().size());
	// System.out.print(dataGrid.getTotal());
	// System.out.print("ok");
	// }
	//
	// @Test
	// public void queryByIdTest() {
	// promotionSellerRuleDefineService.queryById(Long.valueOf(444));
	//
	// }

}
