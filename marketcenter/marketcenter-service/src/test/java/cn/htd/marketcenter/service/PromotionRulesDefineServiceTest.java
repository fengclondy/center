package cn.htd.marketcenter.service;

import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by thinkpad on 2016/12/2.
 */
public class PromotionRulesDefineServiceTest {
	ApplicationContext ctx;
	private PromotionRulesDefineService promotionRulesDefineService;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		promotionRulesDefineService = (PromotionRulesDefineService) ctx.getBean("promotionRulesDefineService");
	}

	// @Test
	// public void addPromotionRuleDefineTest() {
	// PromotionRulesDefineDTO dto = new PromotionRulesDefineDTO();
	// dto.setRuleType("2");
	// dto.setRuleName("卖家规则");
	// dto.setRuleDescribe("这只是一次测试");
	// dto.setDeleted(0);
	// dto.setCreateId(123l);
	// dto.setCreateName("周星驰");
	// dto.setCreateTime(new Date());
	// dto.setModifyId(999l);
	// dto.setModifyName("刘德华");
	// dto.setModifyTime(new Date());
	// promotionRulesDefineService.addPromotionRuleDefine(dto);
	// Long l = dto.getRuleId();
	// System.out.println(l);
	//
	// }
	//
	// @Test
	// public void updateByConditionTest() {
	// PromotionRulesDefineDTO dto = new PromotionRulesDefineDTO();
	// dto.setRuleId(1l);
	// dto.setRuleDescribe("我是一只快乐的小逗比");
	// dto.setModifyId(999l);
	// dto.setModifyName("哇哈哈");
	// dto.setModifyTime(new Date());
	// promotionRulesDefineService.updateByCondition(dto);
	// }
	//
	// @Test
	// public void queryByConditionTest() {
	// PromotionRulesDefineDTO dto = new PromotionRulesDefineDTO();
	// DataGrid<PromotionRulesDefineDTO> dataGrid =
	// promotionRulesDefineService.queryByCondition(dto, null);
	// System.out.println("hah");
	// }
	//
	// @Test
	// public void deleteTest() {
	// PromotionRulesDefineDTO dto = new PromotionRulesDefineDTO();
	// dto.setModifyName("hahah");
	// dto.setModifyId(888l);
	// dto.setRuleId(1l);
	// promotionRulesDefineService.delete(dto);
	// System.out.println("hah");
	// }
	//
	// @Test
	// public void queryByIdTest() {
	// promotionRulesDefineService.queryById(1l);
	//
	// }
	//
	// @Test
	// public void testVerifyRuleName() {
	// boolean result = promotionRulesDefineService.verifyRuleName("会员规则");
	// System.out.println("hehe");
	//
	// }

}
