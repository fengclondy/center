package cn.htd.marketcenter.service;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import cn.htd.marketcenter.dto.TimelimitPurchaseMallInfoDTO;
import cn.htd.marketcenter.dto.TimelimitedConditionDTO;
import cn.htd.marketcenter.dto.TimelimitedInfoDTO;

import com.alibaba.fastjson.JSONObject;

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
//		String messageId = "";
//		String buyercode = "htd1146001";
//		String promotionId = "2171328280492";
//		String buyerGrade = "5";
//		String sellerCode = "htd20070002";
//		Pager<TimelimitedInfoDTO> page = new Pager<TimelimitedInfoDTO>();
//		TimelimitedConditionDTO conditionDTO = new TimelimitedConditionDTO();
//		conditionDTO.setSelleCode(sellerCode);
//		ExecuteResult<DataGrid<PromotionListDTO>> result = timelimitedInfoService
//				.queryPromotionListByCondition(conditionDTO, page);
//		System.out.println(JSONObject.toJSONString(result));
	}
	
	@Test
	public void testQueryTimelimitedList() {
		TimelimitPurchaseMallInfoDTO dto = new TimelimitPurchaseMallInfoDTO();
		dto.setPurchaseFlag(1);
		ExecuteResult<List<TimelimitPurchaseMallInfoDTO>> result = timelimitedInfoService.getTimelimitedInfo(dto);
		for (TimelimitPurchaseMallInfoDTO d : result.getResult()) {
			System.out.println(d.getPromotionId() + "==" + d.getSkuName() + "==" + d.getPreferentialStrength() + "==" + d.getSalesVolume()
					+ "==" + d.getSalesVolumePrice() + "==" + d.getStartTime().getTime());
		}
		System.out.println(JSONObject.toJSONString(result));
	}
}
