package cn.htd.marketcenter.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.Pager;
import cn.htd.marketcenter.dto.TimelimitedConditionDTO;
import cn.htd.marketcenter.dto.TimelimitedListDTO;

public class TimelimitedPurchaseServiceImplTestTest {

	ApplicationContext ctx;
	private TimelimitedPurchaseService timelimitedInfoService;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		timelimitedInfoService = (TimelimitedPurchaseService) ctx.getBean("timelimitedPurchaseService");
	}

	@Test
	public void testQueryTimelimitedListByCondition() {
		String messageId = "";
		String buyercode = "htd1146001";
		String promotionId = "2171328280492";
		String buyerGrade = "5";
		String sellerCode = "htd1000000";
		Pager<TimelimitedListDTO> page = new Pager<TimelimitedListDTO>();
		TimelimitedConditionDTO conditionDTO = new TimelimitedConditionDTO();
		// conditionDTO.setSelleCode(sellerCode);
		timelimitedInfoService.queryTimelimitedListByCondition(conditionDTO, page);
	}

}
