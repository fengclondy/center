package cn.htd.marketcenter.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.htd.common.ExecuteResult;
import cn.htd.marketcenter.dto.PromotionInfoDTO;

/**
 * Created by thinkpad on 2016/12/2.
 */
public class PromotionInfoServiceTest {
	ApplicationContext ctx;
	private PromotionInfoService promotionInfoService;

	@Before
	public void setUp() throws Exception {
		ctx = new ClassPathXmlApplicationContext("test.xml");
		promotionInfoService = (PromotionInfoService) ctx.getBean("promotionInfoService");
	}

	@Test
	public void queryPromotionInfoByPromotionIdTest() {
		List<String> promotionIdList = new ArrayList<String>();
		promotionIdList.add("1");
		ExecuteResult<List<PromotionInfoDTO>> result = promotionInfoService
				.queryPromotionInfoByPromotionId(promotionIdList);
		System.out.print(result.isSuccess());
	}

}
