package cn.htd.marketcenter.service;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.ExecuteResult;
import cn.htd.common.util.DictionaryUtils;
import cn.htd.marketcenter.dto.PromotionCategoryItemRuleDefineDTO;

/**
 * Created by thinkpad on 2016/12/2.
 */
public class PromotionCategoryItemRuleDefineServiceTest {
	ApplicationContext ctx;
	private PromotionCategoryItemRuleDefineService promotionCategoryItemRuleDefineService;
	private DictionaryUtils dictionary;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		dictionary = (DictionaryUtils) ctx.getBean("dictionaryUtils");
		promotionCategoryItemRuleDefineService = (PromotionCategoryItemRuleDefineService) ctx
				.getBean("promotionCategoryItemRuleDefineService");
	}

	// @Test
	// public void addPromotionCIRule() {
	// PromotionCategoryItemRuleDefineDTO dto = new
	// PromotionCategoryItemRuleDefineDTO();
	// dto.setRuleType("3");
	// dto.setRuleName("品类/商品规则");
	// dto.setRuleDescribe("品类/商品规则");
	// dto.setRuleTargetType("1");
	// dto.setTargetItemLimit("");
	// dto.setCreateId(123l);
	// dto.setCreateName("周星驰");
	// dto.setModifyName("刘德华");
	// dto.setModifyId(999l);
	// List<Long> brandIds = new ArrayList<Long>();
	// brandIds.add(101l);
	// brandIds.add(202l);
	// List<String> brandNames = new ArrayList<String>();
	// brandNames.add("三星");
	// brandNames.add("海尔");
	// promotionCIRuleDefineService.addPromotionCIRule(dto);
	//
	// }
	//
	// @Test
	// public void updateByConditionTest() {
	// PromotionCategoryItemRuleDefineDTO promotionBuyerRule = new
	// PromotionCategoryItemRuleDefineDTO();
	// promotionBuyerRule.setRuleId(1l);
	// promotionBuyerRule.setRuleTargetType("2");
	// promotionCIRuleDefineService.updateByCondition(promotionBuyerRule);
	// System.out.print("ok");
	// }
	//
	// @Test
	// public void queryByConditionTest() {
	// PromotionCategoryItemRuleDefineDTO promotionBuyerRule = new
	// PromotionCategoryItemRuleDefineDTO();
	// promotionBuyerRule.setRuleTargetType("2");
	// promotionBuyerRule.setCreateName("周星驰");
	// DataGrid<PromotionCategoryItemRuleDefineDTO> dataGrid =
	// promotionCIRuleDefineService.queryByCondition(promotionBuyerRule,
	// null);
	// String str =
	// dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_ITEM_CATEGORY_TYPE,
	// DictionaryConst.OPT_PROMOTION_ITEM_CATEGORY_TYPE_CATEGORY);
	// String str1 =
	// dictionary.getValueByCode(DictionaryConst.TYPE_PROMOTION_RULE_TYPE,
	// DictionaryConst.OPT_PROMOTION_RULE_TYPE_ITEM_CATEGORY);
	// System.out.print(dataGrid.getRows().size());
	// System.out.print(dataGrid.getTotal());
	// System.out.print("ok");
	// }
	//
	@Test
	public void queryPromotionCategoryItemRuleTest() {
		ExecuteResult<PromotionCategoryItemRuleDefineDTO> result = promotionCategoryItemRuleDefineService
				.queryPromotionCategoryItemRule(Long.valueOf(15));
		assertTrue(result.isSuccess());
	}

}
