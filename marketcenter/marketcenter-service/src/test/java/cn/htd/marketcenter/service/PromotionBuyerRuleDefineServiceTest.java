package cn.htd.marketcenter.service;

import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.util.DictionaryUtils;

/**
 * Created by thinkpad on 2016/11/30.
 */
public class PromotionBuyerRuleDefineServiceTest {
	ApplicationContext ctx;
	private PromotionBuyerRuleDefineService promotionBuyerRuleDefineService;
	private DictionaryUtils dictionary;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		dictionary = (DictionaryUtils) ctx.getBean("dictionaryUtils");
		promotionBuyerRuleDefineService = (PromotionBuyerRuleDefineService) ctx
				.getBean("promotionBuyerRuleDefineService");
	}

	// @Test
	// public void addPromotionBuyerRuleTest() {
	// PromotionBuyerRuleDefineDTO promotionBuyerRule = new
	// PromotionBuyerRuleDefineDTO();
	// promotionBuyerRule.setRuleTargetType("2");
	// promotionBuyerRule.setModifyTime(new Date());
	// promotionBuyerRule.setModifyName("彭于晏");
	// promotionBuyerRule.setModifyId(Long.valueOf(111));
	// promotionBuyerRule.setCreateId(Long.valueOf(222));
	// promotionBuyerRule.setCreateName("周星驰");
	// promotionBuyerRule.setCreateTime(new Date());
	// promotionBuyerRule.setRuleDescribe("会员规则");
	// promotionBuyerRule.setRuleType("1");
	// promotionBuyerRule.setRuleName("会员规则");
	// List<PromotionBuyerDetailDefineDTO> list = new
	// ArrayList<PromotionBuyerDetailDefineDTO>();
	// PromotionBuyerDetailDefineDTO dto1 = new PromotionBuyerDetailDefineDTO();
	// dto1.setMemberName("王大锤");
	// dto1.setMemberId(123l);
	// list.add(dto1);
	// PromotionBuyerDetailDefineDTO dto2 = new PromotionBuyerDetailDefineDTO();
	// dto2.setMemberName("郭靖");
	// dto2.setMemberId(321l);
	// list.add(dto2);
	// promotionBuyerRule.setList(list);
	// promotionBuyerRuleDefineService.addPromotionBuyerRule(promotionBuyerRule);
	// System.out.print("ok");
	// }
	//
	// @Test
	// public void updateByConditionTest() {
	// PromotionBuyerRuleDefineDTO promotionBuyerRule = new
	// PromotionBuyerRuleDefineDTO();
	// promotionBuyerRule.setRuleId(Long.valueOf(444));
	// promotionBuyerRule.setTargetBuyerLevel("2");
	// promotionBuyerRuleDefineService.updateByCondition(promotionBuyerRule);
	// System.out.print("ok");
	// }
	//
	// @Test
	// public void queryByConditionTest() {
	// PromotionBuyerRuleDefineDTO promotionBuyerRule = new
	// PromotionBuyerRuleDefineDTO();
	// promotionBuyerRule.setRuleTargetType("aaa");
	// DataGrid<PromotionBuyerRuleDefineDTO> dataGrid =
	// promotionBuyerRuleDefineService
	// .queryByCondition(promotionBuyerRule, null);
	// String str =
	// dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_BUYER_RULE,
	// DictionaryConst.OPT_PROMOTION_BUYER_RULE_APPIONT);
	// String str1 =
	// dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_RULE_TYPE,
	// DictionaryConst.OPT_PROMOTION_RULE_TYPE_BUYER);
	// System.out.print(dataGrid.getRows().size());
	// System.out.print(dataGrid.getTotal());
	// System.out.print("ok");
	// }
	//
	// @Test
	// public void queryByIdTest() {
	// ExecuteResult<PromotionBuyerRuleDefineDTO> result =
	// promotionBuyerRuleDefineService
	// .queryById(Long.valueOf(555));
	//
	// System.out.print("ok");
	// }

}
